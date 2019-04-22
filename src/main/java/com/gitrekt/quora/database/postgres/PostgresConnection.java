package com.gitrekt.quora.database.postgres;

import com.gitrekt.quora.logging.ServiceLogger;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresConnection {

  public HikariDataSource dataSource;

  public PostgresConnection() {
    initDatasource();
  }

  /**
   * Set Database Pool.
   */
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
    dataSource.addDataSourceProperty("cachePrepStmts", true);
    dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
    dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    dataSource.addDataSourceProperty("rewriteBatchedStatements", true);
    dataSource.setMaximumPoolSize(Integer.parseInt(System.getenv("POSTGRES_THREAD_POOL_COUNT")));

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