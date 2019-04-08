package com.gitrekt.quora.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public class BadRequestException extends ServerException {

  public BadRequestException(String message) {
    super(message);
  }

  @Override
  public HttpResponseStatus getCode() {
    return HttpResponseStatus.BAD_REQUEST;
  }
}

