package com.mjgallery.mjgallery.socket.handler;

import android.util.Log;

import com.mjgallery.mjgallery.MarginLottery;
import com.mjgallery.mjgallery.event.SocketDataEvent;

import org.simple.eventbus.EventBus;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class TimeClientHandler extends SimpleChannelInboundHandler<MarginLottery.MessagePacket> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Log.i("接收数据", "channelRead msg=" + (MarginLottery.MessagePacket) msg);
        if (msg instanceof MarginLottery.MessagePacket) {
            MarginLottery.MessagePacket messagePacket = (MarginLottery.MessagePacket) msg;
            if (messagePacket.getMessageType() == MarginLottery.MessageType.LOTTERY_MESSAGE) {
                if (messagePacket.getMessage().getLotteryListList().size() > 0) {
                    List<MarginLottery.LotteryNumber> lotteryNumbers = messagePacket.getMessage().getLotteryListList();
                    if (lotteryNumbers != null && lotteryNumbers.size() > 0) {
                        EventBus.getDefault().post(new SocketDataEvent(lotteryNumbers));
                    }
                }
            }
        }

    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MarginLottery.MessagePacket msg) throws Exception {
        Log.i("接收数据", "messageReceived msg=" + (MarginLottery.MessagePacket) msg);
    }

//    private void postConnect(long userId) {//服务器主动发给客户端 客户端没有接受到
//        ObjectEvent.MessageEvent messageEvent = new ObjectEvent.MessageEvent();
//        messageEvent.userId = userId;
//        EventBus.getDefault().post(messageEvent);
//    }
}