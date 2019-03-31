package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;

import java.util.HashMap;

public class PostQuestionsV1 extends Command {

  public PostQuestionsV1(HashMap<String, String> args){
    super(args);
  }

  @Override
  public void execute() {
    System.out.println("Executing PostQuestions");
  }
}
