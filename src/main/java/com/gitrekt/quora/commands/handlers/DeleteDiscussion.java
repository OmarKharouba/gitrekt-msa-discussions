package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.PostgresConnection;
import com.gitrekt.quora.database.postgres.handlers.DiscussionsPostgresHandler;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.google.gson.JsonObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.UUID;

public class DeleteDiscussion extends Command {

  public DeleteDiscussion(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException, AuthenticationException {
    checkArguments(new String[]{"discussion_id"});
    DiscussionsPostgresHandler discussionHandler = new DiscussionsPostgresHandler();


    Connection connection = PostgresConnection.getInstance().getConnection();

    String discussionId = (String) args.get("discussion_id");

    // TODO: replace this user ID with the actual current user
    discussionHandler.deleteDiscussion(discussionId,"7b33e4fc-1a41-4661-a4d9-563fc21cd89e");

    JsonObject res = new JsonObject();
    res.addProperty("status_code", 200);
    res.addProperty("message", "Discussion deleted successfully");

    return res;
  }
}
