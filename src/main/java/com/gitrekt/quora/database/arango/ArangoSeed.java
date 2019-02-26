package com.gitrekt.quora.database.arango;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.CollectionEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

public class ArangoSeed {
  private final String seedPath;
  private final String collectionName;
  private final ArangoDB connection;
  private final String dbName;

  public ArangoSeed(String seedPath, String collectionName) {
    connection = ArangoConnection.getInstance().getConnection();
    this.seedPath = seedPath;
    this.collectionName = collectionName;
    dbName = System.getenv("ARANGO_DB");

    createDatabase();
    createCollection();
  }

  public void seedCollection() {
    JsonArray seedObjects = parseSeed();
    try {
      connection
          .db(System.getenv("ARANGO_DB"))
          .collection(collectionName)
          .importDocuments(seedObjects.toString());
    } catch (ArangoDBException exception) {
      System.out.println(exception.getMessage());
    }
  }

  private void createDatabase() {
    Collection<String> dbs = connection.getDatabases();

    for (String db : dbs) {
      if (db.equals(dbName)) {
        return;
      }
    }

    try {
      connection.createDatabase(dbName);
    } catch (ArangoDBException exception) {
      exception.printStackTrace();
    }
  }

  private void createCollection() {
    Collection<CollectionEntity> collections = connection.db(dbName).getCollections();
    for (CollectionEntity entity : collections) {
      if (entity.getName().equals(collectionName)) {
        return;
      }
    }

    try {
      connection.db(dbName).createCollection(collectionName);
    } catch (ArangoDBException exception) {
      exception.printStackTrace();
    }
  }

  private JsonArray parseSeed() {
    Gson gson = new Gson();
    JsonReader reader;
    try {
      reader = new JsonReader(new FileReader(seedPath));
      JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
      return jsonObject.get(collectionName).getAsJsonArray();
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    }
    return null;
  }

  /**
   * Sample Implementation for seeding.
   *
   * @param args
   */
  public static void main(String[] args) {
    String seedPath = "src/main/resources/database/nosql/poll/poll_seed.json";
    ArangoSeed seed = new ArangoSeed(seedPath, "polls");
    seed.seedCollection();
  }
}
