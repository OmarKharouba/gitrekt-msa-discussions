package me.mohamedelzarei.gitrekt.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public class NotFoundException extends ServerException {
  String error;

  public NotFoundException() {
    error = "404 not found.";
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
