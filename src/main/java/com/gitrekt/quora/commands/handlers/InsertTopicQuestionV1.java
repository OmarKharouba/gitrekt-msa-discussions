package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.PostgresConnection;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class InsertTopicQuestionV1 extends Command {
  static String[] argsNames = {"topic_id","question_id"};

  public InsertTopicQuestionV1(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException, AuthenticationException{
    checkArguments(argsNames);

    String topicId = (String) args.get("topic_id");
    String questionId = (String) args.get("question_id");
    return null;
  }
}
