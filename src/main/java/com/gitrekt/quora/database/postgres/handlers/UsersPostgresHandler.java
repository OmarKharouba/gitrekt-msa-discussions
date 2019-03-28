package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UsersPostgresHandler extends PostgresHandler<User> {

  public UsersPostgresHandler() {
    super("Users", User.class);
  }

  /**
   * Inserts a user into the database by calling the `Insert_User` procedure.
   *
   * @param params The parameters to the procedure
   * @throws SQLException if an error occurred when inserting into the database
   */
  public void insertUser(Object... params) throws SQLException {
    int[] types = new int[params.length];
    types[0] = Types.OTHER;
    for (int i = 1; i < params.length; i++) {
      types[i] = Types.VARCHAR;
    }
    super.call("CALL Insert_User(?, ?, ?, ?, ?, ?)", types, params);
  }

  /**
   * Calls the `Get_User_ID_And_Password` SQL Function to retrieve the user's `id` and hashed
   * `password`.
   *
   * @param param The parameter to the procedure (email)
   * @return The user with the corresponding fields populated from the database
   * @throws SQLException if an error occurred when inserting into the database
   */
  public User getUserPassword(Object param) throws SQLException {
    int[] types = new int[1];
    types[0] = Types.VARCHAR;
    ResultSet resultSet = super.call("SELECT * FROM Get_User_ID_And_Password(?)", types, param);
    if (resultSet == null || !resultSet.next()) {
      return null;
    }
    User user = new User();
    user.setUserId(resultSet.getString("id"));
    user.setPassword(resultSet.getString("password"));
    return user;
  }
}
