package com.mjgallery.mjgallery.socket;

import android.util.Log;
import android.widget.Toast;

import com.mjgallery.mjgallery.MarginLottery;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.socket.handler.ClientPoHandlerProto;
import com.mjgallery.mjgallery.socket.handler.TimeClientHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

public class SocketConn {
    private volatile static SocketConn socketConn;
    Bootstrap mBootstrap;
    SocketChannel mSocketChannel;
    private TimerTask reConnTask;
    private TimerTask heartTask;
    private Timer heartTimer;
    private Timer reConnTimer;
    private int connect;

    private SocketConn() {
    }

    public static SocketConn getSocketConn() {
        if (socketConn == null) {
            synchronized (Singleton.class) {
                if (socketConn == null) {
                    socketConn = new SocketConn();
                }
            }
        }
        return socketConn;
    }


    public void conn() {

        try {
            final EventLoopGroup group = new NioEventLoopGroup();
            mBootstrap = new Bootstrap()
                    .group(group)
                    .remoteAddress(AppConstants.HOST, AppConstants.PORT)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(0, 10, 0))
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufDecoder(MarginLottery.MessagePacket.getDefaultInstance()))
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new TimeClientHandler())
                                    .addLast(new ClientPoHandlerProto())//断线重连
                            ;
                        }
                    });

            final ChannelFuture channelFuture = mBootstrap.connect().sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(final ChannelFuture future) throws Exception {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (future.isSuccess()) {
                                    mSocketChannel = (SocketChannel) channelFuture.channel();
                                    Log.i("获取连接状态", "连接成功");
                                    if (mSocketChannel == null) {
                                        Toast.makeText(BaseApplication.getInstance(), "第一次连接socketChannel为空", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(BaseApplication.getInstance(), "连接成功", Toast.LENGTH_SHORT).show();
                                        Log.i("断线重连", "断线重连成功");
                                        stopReConn();
                                        startHeart();
                                        perOnePerformance();//发送心跳包给服务器 保持客户端在线状态，服务器60秒每接受到客户端的心跳包，就会移除客户端
                                        //                                    initUser();
                                    }
                                } else {
                                    // 这里一定要关闭，不然一直重试会引发OOM
                                    future.channel().close();
                                    group.shutdownGracefully();

                                    Log.i("获取连接状态", "连接失败-future.isSuccess()为false" + future.isSuccess());
                                    future.channel().eventLoop().schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.i("获取连接状态", "连接异常-第一次" + future.isSuccess());
                                            //                                        Toast.makeText(mContext, "与服务器连接异常", Toast.LENGTH_SHORT).show();
                                            conn();
                                        }
                                    }, 5, TimeUnit.SECONDS);
                                }
                            }
                        });
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Log.i("获取连接状态", "服务器未开启");
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseApplication.getInstance(), "服务器未开启", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            e.printStackTrace();
        }
    }


    private void perOnePerformance() {
        heartTask = new TimerTask() {
            public void run() {
                sendHeart();
            }
        };
        heartTimer.schedule(heartTask, 5000, 25000);//延迟1秒后，每隔15秒发送心跳包，保持连接
    }

    private void reConn() {
        reConnTask = new TimerTask() {
            public void run() {
                stopHeart();//取消心跳包
                conn();
            }
        };
        reConnTimer.schedule(reConnTask, 1000, 5000);//延迟1秒后，每隔5秒断线重连
    }

    private void stopHeart() {
        if (heartTimer != null) {
            heartTimer.cancel();
            heartTimer = null;
        }
        if (heartTask != null) {
            heartTask.cancel();
            heartTask = null;
        }
    }

    private void stopReConn() {
        if (reConnTimer != null) {
            reConnTimer.cancel();
            reConnTimer = null;
        }
        if (reConnTask != null) {
            reConnTask.cancel();
            reConnTask = null;
        }
    }

    private void startHeart() {
        if (heartTimer == null) {
            heartTimer = new Timer();
        }
    }

    private void startReConn() {
        if (reConnTimer == null) {
            reConnTimer = new Timer();
        }
    }

    private String longToDate(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time / 1000));
    }


    private void sendHeart() {
        MarginLottery.MessagePacket.Builder builder1 = MarginLottery.MessagePacket.newBuilder();
        builder1.setMessageType(MarginLottery.MessageType.HEART_BEAT);
        MarginLottery.HeartBeat.Builder newBuilder = MarginLottery.HeartBeat.newBuilder();
        newBuilder.setUserId("12");
        builder1.setHeartBeat(newBuilder.build());

//        MessageBase.MessagePacket.Builder builder = MessageBase.MessagePacket.newBuilder();
//        builder.setMessageType(MessageBase.MessageType.HEART_BEAT);
//        MessageBase.HeartBeat.Builder heartBuilder = MessageBase.HeartBeat.newBuilder();
//        heartBuilder.setUserId(userId);
//        builder.setHeartBeat(heartBuilder.build());
        if (mSocketChannel == null) {
            Log.i("获取连接状态", "发送心跳失败");
            Toast.makeText(BaseApplication.getInstance(), "发送心跳失败", Toast.LENGTH_SHORT).show();
            return;
        } else {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("获取连接状态", "发送心跳成功");
                        if (connect != 1) {
                            Toast.makeText(BaseApplication.getInstance(), "发送心跳成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            mSocketChannel.writeAndFlush(builder1.build());
        }
    }

}
