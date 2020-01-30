package com.mjgallery.mjgallery.socket.handler;

import android.util.Log;

import com.mjgallery.mjgallery.MarginLottery;
import com.mjgallery.mjgallery.socket.ObjectEvent;

import org.simple.eventbus.EventBus;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientPoHandlerProto extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Log.i("接收数据", "channelRead msg=" + (MarginLottery.MessagePacket) msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.i("获取连接状态", "服务器掉线了   ctx" + ctx.toString());
        postConnect(1);
        //使用过程中断线重连
    }

    private void postConnect(int connectState) {//服务器主动发给客户端 客户端没有接受到
        ObjectEvent.MessageEvent messageEvent = new ObjectEvent.MessageEvent();
        messageEvent.connectState = connectState;
        EventBus.getDefault().post(messageEvent);
    }
}
