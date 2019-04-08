package com.gitrekt.quora.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public abstract class ServerException extends Exception {


  protected ServerException(String message) {
    super(message);
  }

  public abstract HttpResponseStatus getCode();
}
