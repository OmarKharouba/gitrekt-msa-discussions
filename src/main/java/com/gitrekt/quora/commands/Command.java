package com.gitrekt.quora.commands;

import com.gitrekt.quora.database.arango.handlers.ArangoHandler;
import com.gitrekt.quora.database.postgres.handlers.PostgresHandler;
import java.util.HashMap;

public abstract class Command {

  private HashMap<String, String> args;

  private PostgresHandler<?> postgresHandler;

  private ArangoHandler<?> arangoHandler;

  public Command(HashMap<String, String> args) {
    this.args = args;
  }

  public void setPostgresHandler(PostgresHandler<?> postgresHandler) {
    this.postgresHandler = postgresHandler;
  }

  public void setArangoHandler(ArangoHandler<?> arangoHandler) {
    this.arangoHandler = arangoHandler;
  }

  public abstract Object execute();
}
