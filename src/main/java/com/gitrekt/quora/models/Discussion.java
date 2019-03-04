package com.gitrekt.quora.models;

public class Discussion {

  private String id;
  private String title;
  private String body;
  private int subscribersCount;
  private boolean isPublic;
  private String pollId;
  private String topicId;
  private String userId;

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

  public void setPublic(boolean aPublic) {
    isPublic = aPublic;
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
}
