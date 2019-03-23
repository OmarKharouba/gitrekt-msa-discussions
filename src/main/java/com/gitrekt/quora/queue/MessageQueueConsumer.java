package com.gitrekt.quora.queue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.logging.Logger;
import org.graalvm.compiler.nodes.NodeView.Default;

/** Represents a consumer that consumes from the micro-service's queue. */
public class MessageQueueConsumer {

  private static final Logger LOGGER = Logger.getLogger(MessageQueueConsumer.class.getName());

  private static MessageQueueConsumer instance;

  private ConcurrentMap<String, Consumer<JsonObject>> listeners;

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
  private MessageQueueConsumer() throws IOException {
    final String queueName = System.getenv("QUEUE_NAME");

    /*
     * Maps all listeners using the Correlation ID.
     */
    listeners = new ConcurrentHashMap<>();

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
  private DefaultConsumer createConsumer() {
    /*
     * Simple consumer that logs the message.
     */
    return new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(
          String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {

        String correlationId = properties.getCorrelationId();
        JsonObject message =
            new JsonParser().parse(new String(body, StandardCharsets.UTF_8)).getAsJsonObject();
        LOGGER.info(
            String.format(
                "Consuming the received message (%s) with correlationId %s.",
                message, correlationId));

        Consumer<JsonObject> consumer = listeners.get(correlationId);
        if (consumer != null) {
          try {
            consumer.accept(message);
          } catch (Exception exception) {
            LOGGER.info("Error calling listener");
            exception.printStackTrace();
          } finally {
            listeners.remove(correlationId);
          }
        }
      }
    };
  }

  public void addListener(String correlationId, Consumer<JsonObject> consumer) {
    listeners.put(correlationId, consumer);
  }

  /** Closes the RabbitMQ Channel. */
  public void close() throws IOException, TimeoutException {
    channel.close();
  }

  public static MessageQueueConsumer getInstance() throws IOException {
    if (instance != null) {
      return instance;
    }
    synchronized (MessageQueueConsumer.class) {
      if (instance == null) {
        instance = new MessageQueueConsumer();
      }
    }
    return instance;
  }
}
