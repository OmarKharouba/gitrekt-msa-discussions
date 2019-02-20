package me.mohamedelzarei.gitrekt.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import me.mohamedelzarei.gitrekt.config.Config;

import java.net.InetSocketAddress;

public class NettyHttpServer {
  ServerBootstrap bootstrap;
  NioEventLoopGroup bossGroup;
  NioEventLoopGroup workerGroup;

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
  public void listen() {
    try {
      // load host and ip from config
      final Config config = Config.getInstance();

      final String host = config.getProperty("host");
      final int port = Integer.parseInt(config.getProperty("port"));

      // listen to port async
      ChannelFuture future = bootstrap.bind(new InetSocketAddress(host, port));

      future.addListener(
          new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
              if (channelFuture.isSuccess()) {
                System.out.println("Server Listening on http://" + host + ":" + port);
              } else {
                System.err.println("Failed to start server: " + channelFuture.cause().toString());
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

  public static void main(String[] args) {
    NettyHttpServer server = new NettyHttpServer();
    server.init();
  }
}
