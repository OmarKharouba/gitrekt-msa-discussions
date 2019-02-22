package com.gitrekt.quora.server.middlewares;

import com.gitrekt.quora.models.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HandleRequest extends SimpleChannelInboundHandler<Request> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {}
}
