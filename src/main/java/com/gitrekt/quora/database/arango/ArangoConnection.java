package com.gitrekt.quora.database.arango;

import com.arangodb.ArangoDB;
import com.arangodb.entity.LoadBalancingStrategy;

public class ArangoConnection {
  private ArangoDB connection;

  public ArangoConnection() {
    connection =
        new ArangoDB.Builder()
            .host(System.getenv("ARANGO_HOST"), Integer.parseInt(System.getenv("ARANGO_PORT")))
            .user(System.getenv("ARANGO_USER"))
            .password(System.getenv("ARANGO_PASS"))
            .maxConnections(Integer.parseInt(System.getenv("ARANGO_POOLSIZE")))
            .loadBalancingStrategy(LoadBalancingStrategy.ROUND_ROBIN)
            .build();
  }

  public void closeConnection() {
    connection.shutdown();
  }

  public ArangoDB getConnection() {
    return connection;
  }

  private static class ArangoConnectionHelper {
    private static final ArangoConnection INSTANCE = new ArangoConnection();
  }

  public static ArangoConnection getInstance() {
    return ArangoConnectionHelper.INSTANCE;
  }
}
