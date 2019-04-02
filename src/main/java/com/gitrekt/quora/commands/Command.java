package com.gitrekt.quora.commands;

import com.gitrekt.quora.database.postgres.handlers.UsersPostgresHandler;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;

import java.sql.SQLException;
import java.util.HashMap;

public abstract class Command {

  protected HashMap<String, Object> args;

  protected UsersPostgresHandler postgresHandler;

  public Command(HashMap<String, Object> args) {
    this.args = args;
  }


  public void setPostgresHandler(UsersPostgresHandler postgresHandler) {
    this.postgresHandler = postgresHandler;
  }

  protected void checkArguments(String[] requiredArgs) throws BadRequestException {
    StringBuilder stringBuilder = new StringBuilder();
    for (String argument : requiredArgs) {
      if (!args.containsKey(argument) || args.get(argument) == null) {
        stringBuilder.append(String.format("Argument %s is missing", argument)).append("\n");
      }
    }
    if (stringBuilder.length() > 0) {
      throw new BadRequestException(stringBuilder.toString());
    }
  }

  public abstract Object execute()
      throws SQLException, BadRequestException, AuthenticationException;
}
