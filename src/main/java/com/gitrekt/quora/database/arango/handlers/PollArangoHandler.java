package com.gitrekt.quora.database.arango.handlers;

import com.gitrekt.quora.models.Poll;

import java.util.Collection;

public class PollArangoHandler extends ArangoHandler<Poll> {

  public PollArangoHandler() {
    super("polls", Poll.class);
  }

  public Collection<Poll> getPolls() {
    return super.findAll();
  }

  public Poll getPoll(String key) {
    return super.findOne(key);
  }
}
