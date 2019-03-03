package com.gitrekt.quora.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public abstract class ServerException extends RuntimeException {

  protected ServerException(String message) {
    super(message);
  }

  public abstract HttpResponseStatus getCode();
}
