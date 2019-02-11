package me.mohamedelzarei.gitrekt.Config.Server.middlewares;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.DecoderResultProvider;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class JSONDecoder extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        // msg is not valid close
        if (msg.decoderResult() != DecoderResult.SUCCESS) {
            ctx.close();
            return;
        }


        ByteBuf content = msg.content(); // msg body
        Gson gson = new Gson();
        JsonObject body = gson.fromJson(content.toString(StandardCharsets.UTF_8), JsonObject.class);

        ctx.channel().writeAndFlush(body).channel().closeFuture();
    }
}
