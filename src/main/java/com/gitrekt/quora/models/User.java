package com.gitrekt.quora.models;

public class User {

  private String userId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "User{" + "id='" + userId + '\'' + '}';
  }
}
