package com.gitrekt.quora.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public class MethodNotAllowed extends ServerException {
  public MethodNotAllowed() {
    super("405 Method Not Allowed.");
  }

  @Override
  public HttpResponseStatus getCode() {
    return HttpResponseStatus.METHOD_NOT_ALLOWED;
  }
}
