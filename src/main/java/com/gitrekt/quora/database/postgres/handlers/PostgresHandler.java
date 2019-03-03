package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.database.postgres.PostgresConnection;
import java.sql.CallableStatement;
import java.sql.Types;
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
   *
   * @param table Table name.
   * @param mapper Class to map rows to.
   */
  public PostgresHandler(String table, Class<T> mapper) {
    connection = PostgresConnection.getInstance().getConnection();
    tableName = table;
    this.mapper = mapper;
    runner = new QueryRunner(PostgresConnection.getInstance().dataSource);
  }

  /**
   * Find all elements in Table.
   *
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

  protected void insert(String sql, Object... params) throws SQLException {
    // DbUtils does not support calling procedures?
    CallableStatement callableStatement = connection.prepareCall(sql);
    for (int i = 0; i < params.length; i++) {
      if (i == 0) {
        callableStatement.setObject(i + 1, params[i], Types.OTHER);
      } else {
        callableStatement.setString(i + 1, (String) params[i]);
      }
    }
    callableStatement.execute();
  }
}
