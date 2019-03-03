package com.gitrekt.quora.server.middlewares;

import com.gitrekt.quora.models.Request;
import com.gitrekt.quora.queue.MessageQueueConnection;

import com.rabbitmq.client.Channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * This middleware adds the message to the RabbitMQ queue and sends the message back through the
 * pipeline. This is an example producer.
 */
public class MessageQueueProducerMiddleware extends SimpleChannelInboundHandler<Request> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
    Channel channel = MessageQueueConnection.getInstance().createChannel();
    channel.basicPublish("", System.getenv("QUEUE_NAME"), null, msg.toString().getBytes());
    channel.close();
//    JsonObject jsonObject = msg.getBody();
//    HashMap<String, Object> map = new HashMap<>();
//    for (String key : jsonObject.keySet()) {
//      map.put(key, jsonObject.get(key).getAsString());
//    }
//    Command command = new SignUpCommand(map);
//    command.execute();
//    ctx.channel().writeAndFlush(msg).channel().closeFuture();
  }
}
