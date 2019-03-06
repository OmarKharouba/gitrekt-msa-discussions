package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;

import java.util.HashMap;

public class EchoCommand extends Command {

  public EchoCommand(HashMap<String, String> args) {
    super(args);
  }

  @Override
  public void execute() {
    System.out.println("I echo in a thread!.");
  }
}
