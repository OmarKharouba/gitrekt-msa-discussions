package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.handlers.DiscussionsPostgresHandler;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class UnsubscribeToDiscussionCommand extends Command {
    static String[] argsNames = {"discussion_id", "user_id"};

    public UnsubscribeToDiscussionCommand(HashMap<String, Object> args) {
        super(args);
    }

    @Override
    public Object execute() throws SQLException, BadRequestException {
        checkArguments(argsNames);

        UUID discussionId = UUID.fromString((String) args.get("discussion_id"));
        UUID userId = UUID.fromString((String) args.get("user_id"));

        setPostgresHandler(new DiscussionsPostgresHandler());
        ((DiscussionsPostgresHandler)postgresHandler).deleteUserSubscribeDiscussion(userId, discussionId);

        JsonObject res = new JsonObject();
        res.addProperty("status_code",200);
        res.addProperty("message","Unsubscribed from discussion successfully");

        return res;
    }
}
