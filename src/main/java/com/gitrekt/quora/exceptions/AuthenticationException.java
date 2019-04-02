package com.gitrekt.quora.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public class AuthenticationException extends ServerException {

  public AuthenticationException(String message) {
    super(message);
  }

  @Override
  public HttpResponseStatus getCode() {
    return HttpResponseStatus.UNAUTHORIZED;
  }
}
