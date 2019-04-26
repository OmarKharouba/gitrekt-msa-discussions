package java.com.gitrekt.quora.commands;

import com.gitrekt.quora.commands.handlers.SubscribeToDiscussionCommand;
import com.gitrekt.quora.commands.handlers.UnsubscribeToDiscussionCommand;
import com.gitrekt.quora.exceptions.ServerException;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;

public class TestSubscribeToDiscussion {

    @Test
    public void testSubscribe() throws ServerException, SQLException {

        HashMap<String, Object> args = new HashMap<>();

        args.put("user_id", "7b33e4fc-1a41-4661-a4d9-563fc21cd89e");
        args.put("discussion_id", "df13ff9f-3950-4eee-af27-f3fa9e4ea3cb");

        SubscribeToDiscussionCommand cmd = new SubscribeToDiscussionCommand(args);

        cmd.execute();
    }


    @Test
    public void testUnsubscribe() throws ServerException, SQLException {

        HashMap<String, Object> args = new HashMap<>();

        args.put("user_id", "7b33e4fc-1a41-4661-a4d9-563fc21cd89e");
        args.put("discussion_id", "df13ff9f-3950-4eee-af27-f3fa9e4ea3cb");

        UnsubscribeToDiscussionCommand cmd = new UnsubscribeToDiscussionCommand(args);

        cmd.execute();
    }
}
