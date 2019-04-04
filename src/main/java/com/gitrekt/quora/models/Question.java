package com.gitrekt.quora.models;

import java.sql.Timestamp;

public class Question {
  private String id;

  private String userId;

  private String pollId;
  private String title;
  private String body;
  private int upvotes;
  private int subscribers;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp deletedAt;
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public int getUpvotes() {
    return upvotes;
  }

  public void setUpvotes(int upvotes) {
    this.upvotes = upvotes;
  }

  public int getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(int subscribers) {
    this.subscribers = subscribers;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Timestamp getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Timestamp deletedAt) {
    this.deletedAt = deletedAt;
  }

  public String getPollId() { return pollId; }

  public void setPollId(String pollId) { this.pollId = pollId; }

  public String toString(){
    return "Question{" + "question_Id='" + id + '\''
            + ", user_id='" + userId + '\''
            + ", title='" + title + '\''
            + ", body='" + body + '\''
            + ", created_at='" + createdAt.toString() + '\''
            + ", updated_at='" + updatedAt.toString() + '}';
  }
}
