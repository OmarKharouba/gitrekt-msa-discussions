package com.gitrekt.quora.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * Represents a Connection to the RabbitMQ service, that is used to connect and create Channels.
 * This means that the micro-service has one Connection but many Channels. Make sure that separate
 * Threads never share the same Channel.
 */
public class MessageQueueConnection {

  private static final Logger LOGGER = Logger.getLogger(MessageQueueConnection.class.getName());

  /*
   * Connection to the RabbitMQ service.
   */
  private Connection connection;

  private MessageQueueConnection() throws IOException, TimeoutException {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost(System.getenv("RABBIT_HOST"));
    connectionFactory.setPort(Integer.parseInt(System.getenv("RABBIT_PORT")));
    connectionFactory.setUsername(System.getenv("RABBIT_USERNAME"));
    connectionFactory.setPassword(System.getenv("RABBIT_PASSWORD"));

    connection = connectionFactory.newConnection();
  }

  private static class MessageQueueConnectionHelper {

    private static MessageQueueConnection INSTANCE;

    static {
      try {
        INSTANCE = new MessageQueueConnection();
      } catch (IOException | TimeoutException exception) {
        LOGGER.severe("Error creating a connection to RabbitMQ service");
        exception.printStackTrace();
      }
    }
  }

  public static MessageQueueConnection getInstance() {
    return MessageQueueConnectionHelper.INSTANCE;
  }

  /**
   * Creates and returns a Channel to the RabbitMQ service using the current Connection.
   *
   * @return The channel
   * @throws IOException if an error occurred creating the Channel
   */
  public synchronized Channel createChannel() throws IOException {
    return connection.createChannel();
  }

  /**
   * Closes the Connection. This will also close any underlying Channels.
   *
   * @throws IOException if an error occurred closing the Channel
   */
  public void closeConnection() throws IOException {
    connection.close();
  }
}
