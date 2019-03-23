package com.gitrekt.quora.commands;

import java.util.HashMap;

public abstract class Command {

  private HashMap<String, String> args;

  public Command(HashMap<String, String> args) {
    this.args = args;
  }

  public void setArgs(HashMap<String, String> args) {
    this.args = args;
  }

  public abstract Object execute();
}
