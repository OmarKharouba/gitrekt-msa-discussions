package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.models.User;

import java.sql.SQLException;
import java.util.List;

public class UsersPostgresHandler extends PostgresHandler<User> {

  public UsersPostgresHandler() {
    super("Users", User.class);
  }

  public List<User> getUsers() {
    return super.findAll();
  }

  public void insertUser(Object ...params) throws SQLException {
    super.insert("CALL Insert_User(?, ?, ?, ?, ?, ?)", params);
  }
}
