package com.gitrekt.quora.database.postgres;

import com.gitrekt.quora.logging.ServiceLogger;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresConnection {

  public Connection conn;
  public HikariDataSource dataSource;

  public PostgresConnection() {
    initDatasource();
  }

  /** Set Database Pool. */
  private void initDatasource() {
    dataSource = new HikariDataSource();
    String url =
        String.format(
            "jdbc:postgresql://%s:%s/%s",
            System.getenv("POSTGRES_HOST"),
            System.getenv("POSTGRES_PORT"),
            System.getenv("POSTGRES_DB"));
    dataSource.setJdbcUrl(url);
    dataSource.setUsername(System.getenv("POSTGRES_USER"));
    dataSource.setPassword(System.getenv("POSTGRES_PASSWORD"));
    dataSource.addDataSourceProperty("serverName", System.getenv("POSTGRES_HOST"));
    dataSource.addDataSourceProperty("port", System.getenv("POSTGRES_PORT"));
    dataSource.addDataSourceProperty("databaseName", System.getenv("POSTGRES_DB"));
  }

  /**
   * Get connection from the pool.
   */
  public Connection getConnection() {
    try {
      if (dataSource == null || dataSource.isClosed()) {
        initDatasource();
      }
      return dataSource.getConnection();
    } catch (SQLException exception) {
      ServiceLogger.getInstance().log(exception.getMessage());
    }
    return null;
  }

  public void closeConnection() {
    dataSource.close();
  }

  private static class PostgresConnectionHelper {
    private static final PostgresConnection INSTANCE = new PostgresConnection();
  }

  public static PostgresConnection getInstance() {
    return PostgresConnectionHelper.INSTANCE;
  }
}
