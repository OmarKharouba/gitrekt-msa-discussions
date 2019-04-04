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

public class SubscribeToTopic extends Command {
  static String[] argsNames = {"topic_id", "user_id"};

  public SubscribeToTopic(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException, AuthenticationException {
    checkArguments(argsNames);

    Connection connection = PostgresConnection.getInstance().getConnection();

    String topicId = (String) args.get("topic_id");
    String userId = (String) args.get("user_id");

    String sql = "CALL Insert_User_Subscribe_Topic(?, ?)";

    CallableStatement callableStatement = connection.prepareCall(sql);

    callableStatement.setObject(1, UUID.fromString(userId), Types.OTHER);
    callableStatement.setObject(2,UUID.fromString(topicId),Types.OTHER);


    callableStatement.execute();

    JsonObject res = new JsonObject();
    res.addProperty("status_code",200);
    res.addProperty("message","Subscribed to topic successfully");

    return res;
  }
}
