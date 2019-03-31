package com.gitrekt.quora.commands;

import java.util.HashMap;

public abstract class Command implements Runnable {
  protected HashMap<String, String> args;

  public Command(HashMap<String, String> args) {
    this.args = args;
  }

  public void setArgs(HashMap<String, String> args) {
    this.args = args;
  }

  public abstract void execute();

  @Override
  public void run() {
    execute();
  }
}
