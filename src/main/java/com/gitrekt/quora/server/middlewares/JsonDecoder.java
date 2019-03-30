package com.gitrekt.quora.server.middlewares;

import com.gitrekt.quora.exceptions.MethodNotAllowed;
import com.gitrekt.quora.exceptions.NotFoundException;
import com.gitrekt.quora.models.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.nio.charset.StandardCharsets;

public class JsonDecoder extends SimpleChannelInboundHandler<FullHttpRequest> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg)
          throws MethodNotAllowed, NotFoundException {

    // msg is not valid close
    if (msg.decoderResult() != DecoderResult.SUCCESS) {
      ctx.channel().closeFuture();
      return;
    }

    if (msg.method() != HttpMethod.POST) {
      throw new MethodNotAllowed();
    }

    if (!msg.uri().equals("/health")) {
      throw new NotFoundException();
    }


    // try to parse body
    ByteBuf content = msg.content(); // msg body
    Gson gson = new Gson();
    JsonObject body;

    try {
      body = gson.fromJson(content.toString(StandardCharsets.UTF_8), JsonObject.class);
    } catch (Exception exception) {
      body = null;
    }

    // Create request object
    Request request = new Request();
    request.setBody(body);

    //    ctx.writeAndFlush(request);
    ctx.fireChannelRead(request);
  }
}
