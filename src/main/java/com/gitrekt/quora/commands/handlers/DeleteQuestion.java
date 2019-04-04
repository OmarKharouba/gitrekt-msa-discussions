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

public class DeleteQuestion extends Command {

  public DeleteQuestion(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException, AuthenticationException {
    checkArguments(new String[]{"question_id"});

    Connection connection = PostgresConnection.getInstance().getConnection();

    String questionId = (String) args.get("question_id");

    String sql = "CALL Delete_Question(?)";
    CallableStatement callableStatement = connection.prepareCall(sql);

    callableStatement.setObject(1,UUID.fromString(questionId),Types.OTHER);

    callableStatement.execute();

    JsonObject res = new JsonObject();
    res.addProperty("status_code",200);
    res.addProperty("message","Question deleted successfully");

    return res;
  }
}
