package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.PostgresConnection;
import com.gitrekt.quora.database.postgres.handlers.PostgresHandler;
import com.gitrekt.quora.database.postgres.handlers.QuestionsPostgresHandler;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.gitrekt.quora.models.Question;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.UUID;

public class GetQuestion extends Command {
  public GetQuestion(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException, AuthenticationException {
    checkArguments(new String[]{"question_id"});

    String questionId = (String) args.get("question_id");

    QuestionsPostgresHandler handler = new QuestionsPostgresHandler();

    Question question = handler.getQuestion(questionId);



    JsonObject res = new JsonObject();
    res.addProperty("status_code",200);

    JsonObject resBody = new JsonObject();
    resBody.addProperty("question_id",questionId);
    resBody.addProperty("user_id",question.getUserId());
    resBody.addProperty("poll_id",question.getPollId());
    resBody.addProperty("title",question.getTitle());
    resBody.addProperty("body",question.getBody());
    resBody.addProperty("upvotes",question.getUpvotes());
    resBody.addProperty("subscribers",question.getSubscribers());
    resBody.addProperty("created_at",question.getCreatedAt().toString());
    resBody.addProperty("updated_at",question.getUpdatedAt().toString());


    res.add("body",resBody);

    return res;
  }
}
