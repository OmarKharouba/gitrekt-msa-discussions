package com.gitrekt.quora.server.middlewares;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ExceptionHandler extends SimpleChannelInboundHandler {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    ctx.fireChannelRead(msg);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable msg) throws Exception {
    ctx.channel().writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
  }
}
