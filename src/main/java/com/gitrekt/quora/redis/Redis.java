package com.gitrekt.quora.redis;

import redis.clients.jedis.Jedis;

/** Represents the connection to Redis service. */
public class Redis {

  private static Redis instance;

  /** Connection to Redis service. */
  private Jedis redisClient;

  private Redis() {
    String host = System.getenv("REDIS_HOST");
    int port = Integer.parseInt(System.getenv("REDIS_PORT"));
    redisClient = new Jedis(host, port);
  }

  private static class RedisHelper {
    private static final Redis INSTANCE = new Redis();
  }

  public static Redis getInstance() {
    return RedisHelper.INSTANCE;
  }

  /**
   * Returns the connection to the Redis service.
   *
   * @return The Redis connection (client)
   */
  public Jedis getRedisClient() {
    return redisClient;
  }
}
