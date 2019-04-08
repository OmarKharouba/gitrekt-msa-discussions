package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.models.Discussion;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DiscussionsPostgresHandler extends PostgresHandler<Discussion> {

  public DiscussionsPostgresHandler() {
    super("Discussions", Discussion.class);
  }

  /** Adds a new discussion to the database. */
  public void postDiscussion(Object... params) throws SQLException {
    String sql = "CALL Insert_Discussion(?, ?, ?, ?, ?, ?, ?, ?)";
    CallableStatement callableStatement = connection.prepareCall(sql);

    for (int i = 0; i < 8; i++) callableStatement.setObject(i + 1, params[i], Types.OTHER);

    callableStatement.execute();
  }

  public Discussion getDiscussion(String discussionId) throws SQLException {
    String sql = "SELECT * FROM discussions WHERE id=?";

    ResultSet query = call(sql, new int[] {Types.OTHER}, discussionId);

    Discussion discussion = new Discussion();

    for (int i = 1; i <= query.getMetaData().getColumnCount(); i++)
      System.out.println(query.getMetaData().getColumnLabel(i));
    while (query.next()) {
      discussion.setId(query.getString("id"));
      discussion.setTitle(query.getString("title"));
      discussion.setBody(query.getString("body"));
      discussion.setSubscribersCount(query.getInt("subscribers_count"));
      discussion.setPublic(query.getBoolean("is_public"));
      discussion.setPollId(query.getString("poll_id"));
      discussion.setTopicId(query.getString("topic_id"));
      discussion.setUserId(query.getString("user_id"));
    }

    return discussion;
  }
}
