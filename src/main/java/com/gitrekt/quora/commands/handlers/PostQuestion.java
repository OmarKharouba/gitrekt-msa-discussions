package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.handlers.QuestionsPostgresHandler;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * This command adds a new question with the given arguments.
 */

public class PostQuestion extends Command {
  static String[] argsNames = {"user_id","title"};

  public PostQuestion(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException, AuthenticationException {

    checkArguments(argsNames);

    QuestionsPostgresHandler questionHandler;
    questionHandler = new QuestionsPostgresHandler();


    String userId = (String) args.get("user_id");
    String title = (String) args.get("title");
    String body = (String) args.get("body");
    UUID questionId = UUID.randomUUID();

    questionHandler.postQuestion(questionId, UUID.fromString(userId), title, body);


    JsonObject res = new JsonObject();
    res.addProperty("status_code","200");
    JsonObject resBody = new JsonObject();
    resBody.addProperty("question_id",questionId.toString());
    resBody.addProperty("user_id",userId);
    resBody.addProperty("title",title);
    resBody.addProperty("body",body);
    resBody.addProperty("upvotes",0);
    resBody.addProperty("subscribers",0);

    res.add("body", resBody);

    return res;
  }
}