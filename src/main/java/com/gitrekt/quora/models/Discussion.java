package com.gitrekt.quora.models;

import com.google.gson.JsonObject;

import java.sql.Timestamp;

public class Discussion {

  private String id;
  private String title;
  private String body;
  private int subscribersCount;
  private boolean isPublic;
  private String pollId;
  private String topicId;
  private String userId;
  private Timestamp createdAt;
  private Timestamp deletedAt;
  private JsonObject media;

  public JsonObject getMedia() {
    return media;
  }

  public void setMedia(JsonObject media) {
    this.media = media;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Timestamp deletedAt) {
    this.deletedAt = deletedAt;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public int getSubscribersCount() {
    return subscribersCount;
  }

  public boolean isPublic() {
    return isPublic;
  }

  public String getPollId() {
    return pollId;
  }

  public String getTopicId() {
    return topicId;
  }

  public String getUserId() {
    return userId;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setSubscribersCount(int subscribersCount) {
    this.subscribersCount = subscribersCount;
  }

  public void setPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }

  public void setPollId(String pollId) {
    this.pollId = pollId;
  }

  public void setTopicId(String topicId) {
    this.topicId = topicId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Discussion{"
            + "id='"
            + id
            + '\''
            + ", title='"
            + title
            + '\''
            + ", body='"
            + body
            + '\''
            + ", subscribersCount="
            + subscribersCount
            + ", isPublic="
            + isPublic
            + ", pollId='"
            + pollId
            + '\''
            + ", topicId='"
            + topicId
            + '\''
            + ", userId='"
            + userId
            + '\''
            + '}';
  }
}
