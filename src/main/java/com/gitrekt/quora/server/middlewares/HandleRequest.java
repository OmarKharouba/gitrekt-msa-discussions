package com.gitrekt.quora.server.middlewares;

import com.gitrekt.quora.models.Request;
import com.gitrekt.quora.queue.MessageQueueConnection;
import com.gitrekt.quora.queue.MessageQueueConsumer;
import com.google.gson.JsonObject;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class HandleRequest extends SimpleChannelInboundHandler<Request> {

  private static final String DEFAULT_EXCHANGE = "";
  private static final String QUEUE_NAME = System.getenv("QUEUE_NAME");

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Request msg)
      throws IOException, TimeoutException {

    String correlationId = UUID.randomUUID().toString();
    MessageQueueConsumer.getInstance()
        .addListener(
            correlationId,
            new Consumer<JsonObject>() {
              @Override
              public void accept(JsonObject jsonObject) {
                ctx.writeAndFlush(jsonObject);
              }
            });

    Channel channel = MessageQueueConnection.getInstance().createChannel();
    BasicProperties properties =
        new BasicProperties.Builder().replyTo(QUEUE_NAME).correlationId(correlationId).build();
    channel.basicPublish(DEFAULT_EXCHANGE, msg.getQueue(), properties, msg.toString().getBytes());
    channel.close();
  }
}
