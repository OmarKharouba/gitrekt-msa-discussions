package com.gitrekt.quora.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public class NotFoundException extends ServerException {

  private String error;

  public NotFoundException() {
    super("404 not found");
  }

  @Override
  public String getMessage() {
    return error;
  }

  @Override
  public HttpResponseStatus getCode() {
    return HttpResponseStatus.NOT_FOUND;
  }
}
