package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.models.Question;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class QuestionsPostgresHandler extends PostgresHandler<Question> {

  public QuestionsPostgresHandler() {
    super("Questions", Question.class);
  }

  /**
   * Adds a new question to the database.
   */
  public void postQuestion(Object... params) throws SQLException {
    String sql = "CALL Insert_Question(?, ?, ?, ?)";
    CallableStatement callableStatement = connection.prepareCall(sql);

    callableStatement.setObject(1,params[0], Types.OTHER);
    callableStatement.setObject(2,params[1],Types.OTHER);
    callableStatement.setObject(3,params[2], Types.VARCHAR);
    callableStatement.setObject(4,params[3], Types.VARCHAR);


    callableStatement.execute();
  }

  public Question getQuestion(String questionId) throws SQLException {
    String sql = "SELECT * FROM questions WHERE id=?";

    ResultSet query = call(sql,new int[]{Types.OTHER},questionId);

    Question question = new Question();

    for(int i = 1;i<=query.getMetaData().getColumnCount();i++)
      System.out.println(query.getMetaData().getColumnLabel(i));
    while(query.next())
    {
      question.setId(query.getString("id"));
      question.setUserId(query.getString("user_id"));
      question.setPollId(query.getString("poll_id"));
      question.setTitle(query.getString("title"));
      question.setBody(query.getString("body"));
      question.setCreatedAt(query.getTimestamp("created_at"));
      question.setUpdatedAt(query.getTimestamp("updated_at"));
    }

    return question;
  }

}
