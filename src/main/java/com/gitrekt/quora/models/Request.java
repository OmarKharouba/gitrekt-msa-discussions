package com.gitrekt.quora.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

/** Model for HttpRequest. */
public class Request {

  private static final Gson gson = new Gson();

  private String httpMethod;
  private Map<String, List<String>> queryParams;
  private JsonObject body;
  private boolean isAuthenticated;
  private String userId;
  private String path;
  private String jwt;
  private String queue;
  private String command;

  public String getPath() {
    return path;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public String getJwt() {
    return jwt;
  }

  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  public JsonObject getBody() {
    return body;
  }

  public Map<String, List<String>> getQueryParams() {
    return queryParams;
  }

  public String getQueue() {
    return queue;
  }

  public String getCommand() {
    return command;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

  public void setAuthenticated(boolean authenticated) {
    isAuthenticated = authenticated;
  }

  public void setBody(JsonObject body) {
    this.body = body;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setQueryParams(Map<String, List<String>> queryParams) {
    this.queryParams = queryParams;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String toJsonString() {
    return gson.toJson(this, Request.class);
  }

  @Override
  public String toString() {
    return "Request{"
        + "httpMethod='"
        + httpMethod
        + '\''
        + ", queryParams="
        + queryParams
        + ", body="
        + body
        + ", isAuthenticated="
        + isAuthenticated
        + ", userId='"
        + userId
        + '\''
        + ", path='"
        + path
        + '\''
        + ", jwt='"
        + jwt
        + '\''
        + ", queue='"
        + queue
        + '\''
        + ", command='"
        + command
        + '\''
        + '}';
  }
}
