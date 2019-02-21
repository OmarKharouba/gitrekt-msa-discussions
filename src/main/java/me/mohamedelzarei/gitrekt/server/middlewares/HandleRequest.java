package me.mohamedelzarei.gitrekt.server.middlewares;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.mohamedelzarei.gitrekt.models.Request;

public class HandleRequest extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {

    }
}
