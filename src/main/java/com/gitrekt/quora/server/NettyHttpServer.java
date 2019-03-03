package com.gitrekt.quora.server;

import com.gitrekt.quora.database.postgres.handlers.UsersPostgresHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class NettyHttpServer {

  private static final Logger LOGGER = Logger.getLogger(NettyHttpServer.class.getName());

  private ServerBootstrap bootstrap;
  private NioEventLoopGroup bossGroup;
  private NioEventLoopGroup workerGroup;

  public NettyHttpServer() {}

  public void init() {
    this.bootstrapServer();
    this.listen();
  }

  /** Bootstrap the server. */
  public void bootstrapServer() {
    bootstrap = new ServerBootstrap();

    // The boss accepts an incoming connection.
    // The worker handles the traffic of the accepted connection once the boss accepts
    // the connection and registers the accepted connection to the worker.

    bossGroup = new NioEventLoopGroup(1);
    workerGroup = new NioEventLoopGroup();

    // handler registers a channel handler for the parent channel
    // childHandler registers a channel handler for child channels
    // https://stackoverflow.com/a/39440698/1508542

    bootstrap
        .group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.INFO)) // log only for parent
        .childHandler(new HttpServerInitializer())
        .option(ChannelOption.SO_BACKLOG, 128) // max queue length for incoming connections
        .childOption(ChannelOption.SO_KEEPALIVE, true); // tcp keep-alive header
  }

  /** Start the server on host:port. */
  private void listen() {
    try {
      final String host = System.getenv("SERVER_HOST");
      final int port = Integer.parseInt(System.getenv("SERVER_PORT"));

      // listen to port async
      ChannelFuture future = bootstrap.bind(new InetSocketAddress(host, port));

      future.addListener(
          new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
              if (channelFuture.isSuccess()) {
                LOGGER.info(String.format("Server Listening on http://%s:%s", host, port));
              } else {
                LOGGER.severe(
                    String.format("Failed to start server %s", channelFuture.cause().toString()));
              }
            }
          });

      future.channel().closeFuture().sync();
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  /** Start the server. */
  public static void main(String[] args) {
    //    try {
    //      MessageQueueConsumer messageQueueConsumer = new MessageQueueConsumer();
    //    } catch (Exception exception) {
    //      LOGGER.severe(
    //          String.format("Failed to start Message Queue Consumer %s", exception.getMessage()));
    //    }

//    UsersPostgresHandler pg = new UsersPostgresHandler();
//    System.out.println(pg.getUsers());

    NettyHttpServer server = new NettyHttpServer();
    server.init();
  }
}
