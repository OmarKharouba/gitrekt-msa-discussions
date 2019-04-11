package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.database.postgres.PostgresConnection;
import com.gitrekt.quora.models.Discussion;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DiscussionsPostgresHandler extends PostgresHandler<Discussion> {

    public DiscussionsPostgresHandler() {
        super("Discussions", Discussion.class);
    }

    /**
     * Adds a new discussion to the database.
     */
    public void postDiscussion(Object... params) throws SQLException {
        String sql = "CALL Insert_Discussion(?, ?, ?, ?, ?, ?, ?, ?)";
        CallableStatement callableStatement = connection.prepareCall(sql);

        for (int i = 0; i < 8; i++) callableStatement.setObject(i + 1, params[i], Types.OTHER);

        callableStatement.execute();
    }

    public Discussion getDiscussion(String discussionId) throws SQLException {
        String sql = "SELECT * FROM discussions WHERE id=?";

        ResultSet query = call(sql, new int[]{Types.OTHER}, discussionId);

        Discussion discussion = new Discussion();

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

    public void updateDiscussion(String discussionId, Map<String, String> modifiedFields) throws SQLException {

        Connection connection = PostgresConnection.getInstance().getConnection();

        String sql = "UPDATE discussions SET ";
        boolean first = true;
        for (Map.Entry<String, String> e : modifiedFields.entrySet()) {
            if (!first) sql += ", ";
            first = false;
            sql += e.getKey() + "=\'" + e.getValue() + "\'";
        }
        sql += " WHERE id=?";

        CallableStatement callableStatement = connection.prepareCall(sql);

        callableStatement.setObject(1, UUID.fromString(discussionId), Types.OTHER);

        callableStatement.execute();

    }

    public void deleteDiscussion(String discussionId) throws SQLException {
        String sql = "DELETE FROM discussions WHERE id = ?";

        CallableStatement callableStatement = connection.prepareCall(sql);

        callableStatement.setObject(1, UUID.fromString(discussionId), Types.OTHER);


        callableStatement.execute();
    }

}
