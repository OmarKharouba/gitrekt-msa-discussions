package com.gitrekt.quora.queue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/** Represents a consumer that consumes from the micro-service's queue. */
public class MessageQueueConsumer {

  private static final Logger LOGGER = Logger.getLogger(MessageQueueConsumer.class.getName());

  /** Channel to the RabbitMQ service. */
  private Channel channel;

  /**
   * Creates a Message Queue Consumer.
   *
   * <ol>
   *   <li>1. Creating a channel.
   *   <li>2. Declaring a Queue.
   *   <li>3. Creating and Adding the Consumer to the queue.
   * </ol>
   *
   * @throws IOException if an error occurred creating either the Channel or Queue, or when adding
   *     the Consumer.
   */
  public MessageQueueConsumer() throws IOException {
    final String queueName = System.getenv("QUEUE_NAME");
    channel = MessageQueueConnection.getInstance().createChannel();

    /*
     * Declare a queue that is persistent/durable.
     */
    channel.queueDeclare(queueName, true, false, false, null);

    /*
     * Consume messages from the queue, acknowledge when
     * messages are sent to the consumer.
     */
    channel.basicConsume(queueName, true, createConsumer());
  }

  /**
   * Creates a consumer that will consume from the queue.
   *
   * @return The consumer
   */
  private Consumer createConsumer() {
    /*
     * Simple consumer that logs the message.
     */
    return new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(
          String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {

        String message = new String(body, StandardCharsets.UTF_8);
        LOGGER.info(String.format("Consuming the received message (%s).", message));
      }
    };
  }

  /** Closes the RabbitMQ Channel. */
  public void close() throws IOException, TimeoutException {
    channel.close();
  }
}
