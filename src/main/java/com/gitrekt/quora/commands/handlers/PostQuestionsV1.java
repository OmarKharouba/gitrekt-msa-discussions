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
    System.out.println("In command");

    JsonObject res = new JsonObject();
    try {
      checkArguments(argsNames);
    } catch (BadRequestException e) {
      res.addProperty("status_code","402");
      res.addProperty("message","bad request");

      return res;
    }

    System.out.println("Arguments checked");

    QuestionsPostgresHandler questionHandler;
    try{
      questionHandler = new QuestionsPostgresHandler();
    }catch (Exception exc){
      exc.printStackTrace();

      questionHandler = null;
    }

    System.out.println("Get arguments");


    String userId = args.get("user_id");
    String title = args.get("title");
    String body = args.get("body");

    try {
      UUID questionId = UUID.randomUUID();

      System.out.println("Insert in db");

      questionHandler.postQuestion(questionId, UUID.fromString(userId), title, body);

      System.out.println("DB complete");

      res.addProperty("status_code","200");
      JsonObject resBody = new JsonObject();
      resBody.addProperty("question_id",questionId.toString());
      resBody.addProperty("user_id",userId);
      resBody.addProperty("title",title);
      resBody.addProperty("body",body);
      resBody.addProperty("upvotes",0);
      resBody.addProperty("subscribers",0);

      res.add("body", resBody);
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();

      res.addProperty("status_code","402");
      res.addProperty("message","bad request");
    } catch (Exception exc){
      exc.printStackTrace();

      res.addProperty("status_code","500");
      res.addProperty("message","internal server error");
    } finally {
      return res;
    }
  }
}