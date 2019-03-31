package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.models.Question;

import java.sql.CallableStatement;
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

}
