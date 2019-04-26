package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.PostgresConnection;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.google.gson.JsonObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.UUID;

public class SubscribeToDiscussionCommand extends Command {
  static String[] argsNames = {"discussion_id", "userId"};

  public SubscribeToDiscussionCommand(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException {
    checkArguments(argsNames);

    Connection connection = PostgresConnection.getInstance().getConnection();

    String discussionId = (String) args.get("discussion_id");
    String userId = (String) args.get("userId");

    String sql = "CALL Insert_User_Subscribe_Discussion(?, ?)";

    CallableStatement callableStatement = connection.prepareCall(sql);

    callableStatement.setObject(1, UUID.fromString(userId), Types.OTHER);
    callableStatement.setObject(2, UUID.fromString(discussionId),Types.OTHER);

    callableStatement.execute();

    JsonObject res = new JsonObject();
    res.addProperty("status_code",200);
    res.addProperty("message","Subscribed to discussion successfully");

    return res;
  }
}
