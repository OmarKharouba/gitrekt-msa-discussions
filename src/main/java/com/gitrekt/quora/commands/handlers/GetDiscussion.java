package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.handlers.DiscussionsPostgresHandler;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.gitrekt.quora.exceptions.NotFoundException;
import com.gitrekt.quora.exceptions.ServerException;
import com.gitrekt.quora.models.Discussion;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.SQLException;
import java.util.HashMap;

public class GetDiscussion extends Command {
  public GetDiscussion(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public Object execute() throws ServerException, SQLException {
    checkArguments(new String[]{"discussion_id"});

    String discussionId = (String) args.get("discussion_id");

    DiscussionsPostgresHandler handler = new DiscussionsPostgresHandler();

    Discussion discussion = handler.getDiscussion(discussionId);

    if (discussion.getId() == null || discussion.getDeletedAt() != null) {
      throw new NotFoundException();
    }

    JsonObject res = new JsonObject();
    res.addProperty("status_code", 200);

    JsonObject resBody = new JsonObject();
    resBody.addProperty("discussion_id", discussionId);
    resBody.addProperty("title", discussion.getTitle());
    resBody.addProperty("body", discussion.getBody());
    resBody.addProperty("subscribers_count", discussion.getSubscribersCount());
    resBody.addProperty("is_public", discussion.isPublic());
    resBody.addProperty("poll_id", discussion.getPollId());
    resBody.addProperty("topic_id", discussion.getTopicId());
    resBody.addProperty("user_id", discussion.getUserId());
    resBody.addProperty("created_at", discussion.getCreatedAt().toString());
    resBody.add("media", discussion.getMedia() == null ? null : discussion.getMedia());

    res.add("body", resBody);

    return res;
  }
}
