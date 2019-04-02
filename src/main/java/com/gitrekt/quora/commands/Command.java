package com.gitrekt.quora.commands;

import com.gitrekt.quora.exceptions.BadRequestException;
import com.google.gson.JsonObject;

import java.util.HashMap;

public abstract class Command implements Runnable {
  protected static final JsonObject BAD_REQUEST, INTERNAL_SERVER_ERROR, OK;
  static {
    BAD_REQUEST = new JsonObject();

    BAD_REQUEST.addProperty("status_code","402");
    BAD_REQUEST.addProperty("message","bad request");

    INTERNAL_SERVER_ERROR = new JsonObject();

    INTERNAL_SERVER_ERROR.addProperty("status_code","500");
    INTERNAL_SERVER_ERROR.addProperty("message","internal server error");

    OK = new JsonObject();

    OK.addProperty("status_code","200");
    OK.addProperty("message","request successful");
  }
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

  protected boolean checkArguments(String[] requiredArgs){
    StringBuilder stringBuilder = new StringBuilder();
    for (String argument : requiredArgs) {
      if (!args.containsKey(argument) || args.get(argument) == null) {
        stringBuilder.append(String.format("Argument %s is missing", argument)).append("\n");
      }
    }
    if (stringBuilder.length() > 0) {
      return false;
    }

    return true;
  }
}
