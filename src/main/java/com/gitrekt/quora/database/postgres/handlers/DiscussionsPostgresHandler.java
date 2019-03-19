package com.gitrekt.quora.database.postgres.handlers;

import com.gitrekt.quora.models.Discussion;

import java.util.List;

public class DiscussionsPostgresHandler extends PostgresHandler<Discussion> {

  public DiscussionsPostgresHandler() {
    super("Discussions", Discussion.class);
  }

  public List<Discussion> getDiscussions() {
    return super.findAll();
  }


  public static void main(String[] args) {
    // Ecxeption because of environment variable while creating the DB connection
    // check how to fix that
    DiscussionsPostgresHandler h = new DiscussionsPostgresHandler();
    List<Discussion> l = h.findAll();
    for (Discussion d : l) {
      System.out.println(d);
    }
  }
}
