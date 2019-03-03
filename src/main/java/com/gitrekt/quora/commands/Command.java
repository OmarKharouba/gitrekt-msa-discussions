package com.gitrekt.quora.commands;

import com.gitrekt.quora.exceptions.BadRequestException;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class Command {

  protected HashMap<String, Object> args;

  public Command(HashMap<String, Object> args) {
    this.args = args;
  }

  public void setArgs(HashMap<String, Object> args) {
    this.args = args;
  }

  public abstract void execute() throws SQLException;

  protected void checkArguments(String[] requiredArgs) {
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
}
