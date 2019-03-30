package com.gitrekt.quora.server.middlewares;

import com.gitrekt.quora.exceptions.ServerException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.*;

public class JsonEncoder extends ChannelOutboundHandlerAdapter {
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
          throws Exception {

    Gson gson = new Gson();
    HttpResponseStatus status = HttpResponseStatus.OK;
    ByteBuf responseBytes;
    responseBytes = ctx.alloc().buffer();

    if (msg instanceof ServerException) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("Error", ((ServerException) msg).getMessage());
      responseBytes.writeBytes(gson.toJson(jsonObject).getBytes());
      status = ((ServerException) msg).getCode();
    } else if (msg instanceof Exception) {
      JsonObject jsonObject = new JsonObject();
      String err = ((Exception) msg).getMessage();
      if (err == null) {
        err = "An error occurred processing this request.";
      }
      jsonObject.addProperty("Error", err);
      responseBytes.writeBytes(gson.toJson(jsonObject).getBytes());
      status = HttpResponseStatus.BAD_REQUEST;
    } else {
      responseBytes.writeBytes(gson.toJson(msg).getBytes());
    }

    FullHttpResponse response =
            new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, responseBytes);

    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
    response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
    response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
  }
}
