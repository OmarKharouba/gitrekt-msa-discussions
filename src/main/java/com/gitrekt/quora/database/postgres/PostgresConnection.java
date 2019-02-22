package com.gitrekt.quora.database.postgres;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresConnection {

  public Connection conn;
  private HikariDataSource dataSource;

  public PostgresConnection() {
    initDatasource();
    connect();
  }

  /** Set Database Pool. */
  private void initDatasource() {
    dataSource = new HikariDataSource();
    dataSource.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
    dataSource.setUsername(System.getenv("POSTGRES_USER"));
    dataSource.setPassword(System.getenv("POSTGRES_PASSWORD"));
    dataSource.addDataSourceProperty("serverName", System.getenv("POSTGRES_HOST"));
    dataSource.addDataSourceProperty("port", System.getenv("POSTGRES_PORT"));
    dataSource.addDataSourceProperty("databaseName", System.getenv("POSTGRES_DB"));
  }

  /** Connect to database. */
  private void connect() {
    try {
      this.conn = dataSource.getConnection();
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException exception) {
      System.out.println(exception.getMessage());
    }
  }

  private static class PostgresConnectionHelper {
    private static final PostgresConnection INSTANCE = new PostgresConnection();
  }

  public static PostgresConnection getInstance() {
    return PostgresConnectionHelper.INSTANCE;
  }
}
