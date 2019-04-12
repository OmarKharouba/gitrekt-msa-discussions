package com.gitrekt.quora.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public class NotFoundException extends ServerException {

  public NotFoundException() {
    super("404 not found.");
  }

  @Override
  public HttpResponseStatus getCode() {
    return HttpResponseStatus.NOT_FOUND;
  }
}
