package me.mohamedelzarei.gitrekt.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import me.mohamedelzarei.gitrekt.server.middlewares.EchoJsonMiddleware;
import me.mohamedelzarei.gitrekt.server.middlewares.JSONDecoder;
import me.mohamedelzarei.gitrekt.server.middlewares.JSONEncoder;

public class HTTPServerInitalizer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin()
                .allowedRequestHeaders("X-Requested-With", "Content-Type", "Content-Length")
                .allowedRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS)
                .build();

        ChannelPipeline p = socketChannel.pipeline();
//        p.addLast(new CorsHandler(corsConfig));
        p.addLast("codec", new HttpServerCodec());
        p.addLast(new JSONEncoder());
        p.addLast("aggregator", new HttpObjectAggregator(Short.MAX_VALUE));
        p.addLast("compressor", new HttpContentCompressor());
        p.addLast(new JSONDecoder());

        // sample middleware
        p.addLast(new EchoJsonMiddleware());
        // add your own middleware here

    }
}
