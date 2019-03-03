package com.gitrekt.quora.models;

public class User {

  private String userId;
  private String hashedPassword;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  @Override
  public String toString() {
    return "User{" + "userId='" + userId + '\'' + ", password='" + hashedPassword + '\'' + '}';
  }
}
