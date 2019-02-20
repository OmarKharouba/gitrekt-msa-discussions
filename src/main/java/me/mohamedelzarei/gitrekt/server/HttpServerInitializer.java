package me.mohamedelzarei.gitrekt.server;

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

import me.mohamedelzarei.gitrekt.server.middlewares.EchoJsonMiddleware;
import me.mohamedelzarei.gitrekt.server.middlewares.JsonDecoder;
import me.mohamedelzarei.gitrekt.server.middlewares.JsonEncoder;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    CorsConfig corsConfig =
        CorsConfigBuilder.forAnyOrigin()
            .allowedRequestHeaders("X-Requested-With", "Content-Type", "Content-Length")
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
    pipeline.addLast(new EchoJsonMiddleware());
    // add your own middleware here

  }
}
