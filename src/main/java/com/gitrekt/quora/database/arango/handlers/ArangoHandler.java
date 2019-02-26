package com.gitrekt.quora.database.arango.handlers;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.gitrekt.quora.database.arango.ArangoConnection;
import com.gitrekt.quora.models.Poll;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ArangoHandler<T> {
  protected final ArangoDB connection;
  protected final String dbName;
  protected final String collectionName;
  protected Class<T> mapper;

  public ArangoHandler(String collectionName, Class mapper) {
    this.connection = ArangoConnection.getInstance().getConnection();
    this.dbName = System.getenv("ARANGO_DB");
    this.collectionName = collectionName;
    this.mapper = mapper;
  }

  protected Collection<T> findAll() {
    String query = String.format("FOR s in %s RETURN s", collectionName);
    ArrayList<T> elements = new ArrayList<>();

    try {
      ArangoCursor<T> cursor = connection.db(dbName).query(query, mapper);
      while (cursor.hasNext()) {
        elements.add(cursor.next());
      }
    } catch (ArangoDBException exception) {
      exception.printStackTrace();
    }
    return elements;
  }

  protected T findOne(String key) {
    T document = null;
    try {
      document = connection.db(dbName).collection(collectionName).getDocument(key, mapper);
    } catch (ArangoDBException exception) {
      exception.printStackTrace();
    }
    return document;
  }
}
