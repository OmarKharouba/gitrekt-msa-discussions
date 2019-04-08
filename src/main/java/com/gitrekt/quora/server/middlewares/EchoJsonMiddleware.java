package com.gitrekt.quora.server.middlewares;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class EchoJsonMiddleware extends ChannelOutboundHandlerAdapter {
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    // echo back the msg
    Object o=new DiscussionsPostgresHandler().getDiscussions();
    ctx.write(o);
  }

  static class Pair{
    int x,y;
    Pair(int a,int b){
      x=a;
      y=b;
    }
  }
}
