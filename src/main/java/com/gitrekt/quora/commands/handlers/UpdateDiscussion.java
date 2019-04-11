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
import java.util.Map;
import java.util.UUID;

/**
 * This command adds a new discussion with the given arguments.
 */
public class UpdateDiscussion extends Command {
    static String[] argsNames = {"discussion_id"};

    public UpdateDiscussion(HashMap<String, Object> args) {
        super(args);
    }

    @Override
    public Object execute() throws SQLException, BadRequestException, AuthenticationException {

        checkArguments(argsNames);

        DiscussionsPostgresHandler discussionHandler = new DiscussionsPostgresHandler();

        Connection connection = PostgresConnection.getInstance().getConnection();

        String discussionId = (String) args.get("discussion_id");

        HashMap<String, String> modifiedFields = new HashMap<>();

        if (args.containsKey("title")) modifiedFields.put("title", (String) args.get("title"));

        if (args.containsKey("body")) modifiedFields.put("body", (String) args.get("body"));

        if (args.containsKey("is_public"))
            modifiedFields.put("is_public", (String) args.get("is_public"));

        if (args.containsKey("topic_id")) modifiedFields.put("topic_id", (String) args.get("topic_id"));

        if (!modifiedFields.isEmpty())
            discussionHandler.updateDiscussion(discussionId, modifiedFields);


        JsonObject res = new JsonObject();
        res.addProperty("status_code", 200);
        res.addProperty("message", "Discussion updated successfully");

        return res;
    }
}
