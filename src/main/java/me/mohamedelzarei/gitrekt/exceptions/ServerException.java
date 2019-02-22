package me.mohamedelzarei.gitrekt.exceptions;

import io.netty.handler.codec.http.HttpResponseStatus;

public abstract class ServerException extends Exception {
  public abstract HttpResponseStatus getCode();
}
