package com.gitrekt.quora.commands;

import com.gitrekt.quora.exceptions.BadRequestException;

import java.util.HashMap;

public abstract class Command implements Runnable {
  protected HashMap<String, String> args;

  public Command(HashMap<String, String> args) {
    this.args = args;
  }

  public void setArgs(HashMap<String, String> args) {
    this.args = args;
  }

  public abstract Object execute();

  @Override
  public void run() {
    execute();
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
}
