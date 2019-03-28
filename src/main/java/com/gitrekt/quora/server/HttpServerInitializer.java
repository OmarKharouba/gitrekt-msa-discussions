package com.gitrekt.quora.server;

import com.gitrekt.quora.server.middlewares.EchoJsonMiddleware;
import com.gitrekt.quora.server.middlewares.JsonDecoder;
import com.gitrekt.quora.server.middlewares.JsonEncoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    CorsConfig corsConfig =
        CorsConfigBuilder.forAnyOrigin()
            .allowedRequestHeaders(
                "X-Requested-With", "Content-Type", "Content-Length", "Authorization")
            .allowedRequestMethods(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.DELETE,
                HttpMethod.OPTIONS)
            .build();

    ChannelPipeline pipeline = socketChannel.pipeline();
    pipeline.addLast(new CorsHandler(corsConfig));
    pipeline.addLast("codec", new HttpServerCodec());
    pipeline.addLast(new JsonEncoder());
    pipeline.addLast("aggregator", new HttpObjectAggregator(Short.MAX_VALUE));
    pipeline.addLast("compressor", new HttpContentCompressor());
    pipeline.addLast(new JsonDecoder());

    // sample middleware
    // pipeline.addLast(new EchoJsonMiddleware());
    // add your own middleware here
    pipeline.addLast(new HandleRequest());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
    System.err.println("ERROR");
  }
}
