package com.gitrekt.quora.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a Connection to the RabbitMQ service, that is used to connect and create Channels.
 * This means that the micro-service has one Connection but many Channels. Make sure that separate
 * Threads never share the same Channel.
 */
public class MessageQueueConnection {

  private static final Logger LOGGER = Logger.getLogger(MessageQueueConnection.class.getName());

  private static MessageQueueConnection instance = new MessageQueueConnection();
  private static long retryDelay = 3000;

  /*
   * Connection to the RabbitMQ service.
   */
  private Connection connection;

  private MessageQueueConnection() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setAutomaticRecoveryEnabled(true);

    connectionFactory.setHost(System.getenv("RABBIT_HOST"));
    connectionFactory.setPort(Integer.parseInt(System.getenv("RABBIT_PORT")));
    connectionFactory.setUsername(System.getenv("RABBIT_USERNAME"));
    connectionFactory.setPassword(System.getenv("RABBIT_PASSWORD"));

    boolean isConnected = false;
    while (!isConnected) {
      try {
        connection = connectionFactory.newConnection();
        isConnected = true;
      } catch (Exception exception) {
        try {
          Thread.sleep(retryDelay);
          LOGGER.log(
              Level.ALL,
              String.format("Problem connecting with RabbitMQ waiting %d ms", retryDelay));
        } catch (InterruptedException interruptedException) {
            // TODO:: Do something useful
        }
      }
    }
  }

  /**
   * Returns the Singleton Instance.
   *
   * @return The Message Queue Connection Instance
   */
  public static MessageQueueConnection getInstance() {
    return instance;
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
