package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.handlers.DiscussionsPostgresHandler;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/** This command adds a new discussion with the given arguments. */
public class PostDiscussion extends Command {
  static String[] argsNames = {"user_id", "title", "body", "is_public", "poll_id", "topic_id"};

  public PostDiscussion(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws SQLException, BadRequestException, AuthenticationException {

    checkArguments(argsNames);

    DiscussionsPostgresHandler discussionHandler = new DiscussionsPostgresHandler();

    String userId = (String) args.get("user_id");
    String title = (String) args.get("title");
    String body = (String) args.get("body");
    String isPublic = (String) args.get("is_public");
    String pollId = (String) args.get("poll_id");
    String topicId = (String) args.get("topic_id");
    UUID discussionId = UUID.randomUUID();

    discussionHandler.postDiscussion(
        discussionId,
        title,
        body,
        0,
        isPublic,
        UUID.fromString(pollId),
        UUID.fromString(topicId),
        UUID.fromString(userId));

    JsonObject res = new JsonObject();
    res.addProperty("status_code", 200);
    JsonObject resBody = new JsonObject();
    resBody.addProperty("discussion_id", discussionId.toString());
    resBody.addProperty("title", title);
    resBody.addProperty("body", body);
    resBody.addProperty("subscribers_count", 0);
    resBody.addProperty("is_public", isPublic);
    resBody.addProperty("poll_id", pollId);
    resBody.addProperty("topic_id", topicId);
    resBody.addProperty("user_id", userId);

    res.add("body", resBody);

    return res;
  }
}
