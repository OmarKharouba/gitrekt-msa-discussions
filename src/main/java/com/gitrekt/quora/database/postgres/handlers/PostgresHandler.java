package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.database.postgres.PostgresConnection;

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
<<<<<<< HEAD
  public PostgresHandler(String table, Class mapper) {
    System.out.println("Creating db connection");
=======
  public PostgresHandler(String table, Class<T> mapper) {
>>>>>>> ed2fee5b1e366f13b7670509f41599ebd561c702
    connection = PostgresConnection.getInstance().getConnection();
    System.out.println("DB conn. established" + connection.toString());
    tableName = table;
    this.mapper = mapper;
<<<<<<< HEAD
    System.out.println("runner");
    runner = new QueryRunner(PostgresConnection.getInstance().dataSource);
    System.out.println("Runner finished");
=======
>>>>>>> ed2fee5b1e366f13b7670509f41599ebd561c702
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
