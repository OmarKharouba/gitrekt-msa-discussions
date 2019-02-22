package me.mohamedelzarei.gitrekt.server.middlewares;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import me.mohamedelzarei.gitrekt.database.postgres.PostgresConnection;
import me.mohamedelzarei.gitrekt.models.Request;

public class EchoJsonMiddleware extends ChannelOutboundHandlerAdapter {
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    // echo back the msg
    ctx.write(msg);
  }
}
