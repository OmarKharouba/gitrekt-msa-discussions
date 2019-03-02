package com.gitrekt.quora.database.postgres.handlers;

import com.arangodb.ArangoDB;
import com.gitrekt.quora.database.postgres.PostgresConnection;
import com.gitrekt.quora.models.User;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class PostgresHandler<T> {
  protected final Connection connection;
  protected final String tableName;
  protected final QueryRunner runner;
  protected Class<T> mapper;

  /**
   * Postgres Handler Constructor.
   * @param table Table name.
   * @param mapper Class to map rows to.
   */
  public PostgresHandler(String table, Class mapper) {
    connection = PostgresConnection.getInstance().getConnection();
    tableName = table;
    this.mapper = mapper;
    runner = new QueryRunner(PostgresConnection.getInstance().dataSource);
  }

  /**
   * Find all elements in Table.
   * @return List of T Objects.
   */
  public List<T> findAll() {
    try {
      ResultSetHandler<List<T>> elements =
          new BeanListHandler<T>(mapper, new BasicRowProcessor(new GenerousBeanProcessor()));
      return runner.query(String.format("SELECT * FROM %s", tableName), elements);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }
}