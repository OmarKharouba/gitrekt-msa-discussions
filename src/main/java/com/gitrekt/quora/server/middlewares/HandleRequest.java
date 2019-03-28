package com.gitrekt.quora.server.middlewares;

import com.gitrekt.quora.models.Request;
import com.gitrekt.quora.queue.MessageQueueConnection;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class HandleRequest extends SimpleChannelInboundHandler<Request> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Request msg) {
    //    try {
    //      Channel channel = MessageQueueConnection.getInstance().createChannel();
    //      channel.basicPublish(
    //          "",
    //          System.getenv("QUEUE_NAME"),
    //          new BasicProperties.Builder().build(),
    //          msg.toJsonString().getBytes(StandardCharsets.UTF_8));
    //      channel.close();
    //    } catch (IOException | TimeoutException e) {
    //      e.printStackTrace();
    //    }
    ctx.channel().writeAndFlush(msg).channel().closeFuture();
  }
}
