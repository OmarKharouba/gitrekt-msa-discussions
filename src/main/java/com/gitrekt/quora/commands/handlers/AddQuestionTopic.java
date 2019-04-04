package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.PostgresConnection;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.google.gson.JsonObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.UUID;

public class AddQuestionTopic extends Command {
  static String[] argsNames = {"topic_id","question_id"};

  public AddQuestionTopic(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException, AuthenticationException {
    checkArguments(argsNames);

    Connection connection = PostgresConnection.getInstance().getConnection();

    String topicId = (String) args.get("topic_id");
    String questionId = (String) args.get("question_id");

    String sql = "CALL Insert_Question_Topic(?, ?)";
    CallableStatement callableStatement = connection.prepareCall(sql);

    callableStatement.setObject(1, UUID.fromString(topicId), Types.OTHER);
    callableStatement.setObject(2,UUID.fromString(questionId),Types.OTHER);


    callableStatement.execute();

    JsonObject res = new JsonObject();
    res.addProperty("status_code",200);
    res.addProperty("message","Topic added successfully");

    return res;
  }
}
