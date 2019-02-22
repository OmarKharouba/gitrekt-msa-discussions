package me.mohamedelzarei.gitrekt.server.middlewares;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import me.mohamedelzarei.gitrekt.exceptions.NotFoundException;
import me.mohamedelzarei.gitrekt.models.Request;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JsonDecoder extends SimpleChannelInboundHandler<FullHttpRequest> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg)
      throws NotFoundException {

    // msg is not valid close
    if (msg.decoderResult() != DecoderResult.SUCCESS) {
      ctx.channel().closeFuture();
      return;
    }

    // Decode Path
    QueryStringDecoder decoder = new QueryStringDecoder(msg.uri());
    String[] path = decoder.path().substring(1).split("/");

    // Invalid Request
    if (path.length < 3) {
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

    // Http Method, Path without queryParams, Query Params
    // Body if found

    request.setHttpMethod(msg.method().toString());
    request.setPath(decoder.path());
    request.setQueryParams(decoder.parameters());
    request.setBody(body);

    // Parse the queue name and command from the url
    // either command is in uri as in @1 or
    // extract command from path and httpMethod @2
    // URI: /api/v1/users/follow?id=1
    // PATH: /api/v1/users/follow @1
    // PATH: /api/v1/users?id=1 @2

    request.setQueue(path[2] + '-' + path[1] + '-' + "queue"); // API versioned ex: users-v1-queue

    String service = path[2].substring(0, 1).toUpperCase() + path[2].substring(1);

    if (path.length > 3) {
      request.setCommand(path[3] + service + "V1"); // followUsersV1
    } else {
      request.setCommand(request.getHttpMethod() + service + "V1"); // GetUsersV1
    }

    // JWT token
    // set token if auth header is set
    // format: Bearer xxxxxx-xxxxxx-xxxxxx

    System.out.println(msg.headers());
    String authHeader = msg.headers().get("Authorization");
    if (authHeader != null) {
      String[] auth = authHeader.split(" ");
      if (auth.length > 1) {
        request.setJwt(auth[1]);
      }
    }
    ctx.channel().writeAndFlush(request).channel().closeFuture();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.channel().writeAndFlush(cause).channel().closeFuture();
  }
}
