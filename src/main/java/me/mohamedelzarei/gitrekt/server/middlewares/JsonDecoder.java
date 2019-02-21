package me.mohamedelzarei.gitrekt.server.middlewares;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;

import java.nio.charset.StandardCharsets;

public class JsonDecoder extends SimpleChannelInboundHandler<FullHttpRequest> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {

    // msg is not valid close
    if (msg.decoderResult() != DecoderResult.SUCCESS) {
      ctx.channel().closeFuture();
      return;
    }

    ByteBuf content = msg.content(); // msg body
    Gson gson = new Gson();
    JsonObject body = gson.fromJson(content.toString(StandardCharsets.UTF_8), JsonObject.class);

    ctx.channel().writeAndFlush(body).channel().closeFuture();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("Error", cause.getMessage());

    ctx.channel().writeAndFlush(jsonObject).channel().closeFuture();
  }
}
