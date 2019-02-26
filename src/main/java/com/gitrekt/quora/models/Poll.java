package com.gitrekt.quora.models;

import com.arangodb.entity.DocumentField;

import java.util.Arrays;

public class Poll {
  public static class Option {
    String id;
    String title;

    public String getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    @Override
    public String toString() {
      return "Option{" + "id='" + id + '\'' + ", title='" + title + '\'' + '}';
    }
  }

  public static class Vote {
    String id;
    String optionId;

    public String getId() {
      return id;
    }

    public String getOptionId() {
      return optionId;
    }

    @Override
    public String toString() {
      return "Vote{" + "id='" + id + '\'' + ", optionId='" + optionId + '\'' + '}';
    }
  }

  @DocumentField(DocumentField.Type.KEY)
  String id;

  String title;

  Option[] options;

  Vote[] votes;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Option[] getOptions() {
    return options;
  }

  public Vote[] getVotes() {
    return votes;
  }

  @Override
  public String toString() {
    return "Poll{"
        + "id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", options="
        + Arrays.toString(options)
        + ", votes="
        + Arrays.toString(votes)
        + '}';
  }
}
