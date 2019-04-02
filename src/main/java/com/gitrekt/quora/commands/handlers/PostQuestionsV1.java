package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.handlers.QuestionsPostgresHandler;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class PostQuestionsV1 extends Command {
  static String[] argsNames = {"user_id","title"};

  public PostQuestionsV1(HashMap<String, String> args) {
    super(args);
  }

  @Override
  public Object execute() {

    if(!checkArguments(argsNames)) {
      return BAD_REQUEST;
    }

    QuestionsPostgresHandler questionHandler;
    try{
      questionHandler = new QuestionsPostgresHandler();
    }catch (Exception exc){
      exc.printStackTrace();

      questionHandler = null;
    }


    String userId = args.get("user_id");
    String title = args.get("title");
    String body = args.get("body");

    try {
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
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();

      return BAD_REQUEST;
    } catch (Exception exc) {
      exc.printStackTrace();

      return INTERNAL_SERVER_ERROR;
    }
  }
}