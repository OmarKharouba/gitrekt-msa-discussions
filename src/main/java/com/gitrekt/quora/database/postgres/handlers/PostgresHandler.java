package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.database.postgres.PostgresConnection;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class PostgresHandler<T> {

  protected final Connection connection;
  protected final String tableName;
  protected Class<T> mapper;

  /**
   * Postgres Handler Constructor.
   *
   * @param table Table name.
   * @param mapper Class to map rows to.
   */

  public PostgresHandler(String table, Class<T> mapper) {
    connection = PostgresConnection.getInstance().getConnection();
    System.out.println("DB conn. established" + connection.toString());
    tableName = table;
    this.mapper = mapper;
  }

  protected ResultSet call(String sql, int[] types, Object... params) throws SQLException {
    // DbUtils does not support calling procedures?
    CallableStatement callableStatement = connection.prepareCall(sql);
    for (int i = 0;i < types.length;i++) {
      callableStatement.setObject(i + 1, params[i], types[i]);
    }
    boolean containsResults = callableStatement.execute();
    if (containsResults) {
      return callableStatement.getResultSet();
    }
    return null;
  }
}
