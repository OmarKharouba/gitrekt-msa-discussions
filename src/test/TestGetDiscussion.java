import com.gitrekt.quora.commands.handlers.GetDiscussion;
import com.gitrekt.quora.exceptions.ServerException;
import com.gitrekt.quora.models.Discussion;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TestGetDiscussion {

  @Test
  public void testGetDisuussion() throws ServerException, SQLException {
    HashMap<String,Object> args=new HashMap<>();
    args.put("discussion_id","");
    GetDiscussion getDiscussionCommand=new GetDiscussion(args);
    Discussion discussion=(Discussion) getDiscussionCommand.execute();

  }


}
