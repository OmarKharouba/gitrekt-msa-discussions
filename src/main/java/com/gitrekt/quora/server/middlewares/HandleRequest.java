package com.gitrekt.quora.server.middlewares;

import com.gitrekt.quora.config.Config;
import com.gitrekt.quora.controller.HealthController;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.gitrekt.quora.models.Request;
import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;

public class HandleRequest extends SimpleChannelInboundHandler<Request> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Request msg)
      throws BadRequestException, NoSuchMethodException, InvocationTargetException,
          IllegalAccessException {

    JsonObject body = msg.getBody();
    String requestFuncName =
        body.get("function_name").isJsonNull() ? "" : body.get("function_name").getAsString();

    // invalid request
    if (requestFuncName.equals("")) {
      throw new BadRequestException("Invalid body.");
    }

    String funcToCall = Config.getInstance().getProperty(requestFuncName);
    if (funcToCall == null) {
      throw new BadRequestException("Invalid body.");
    }

    boolean result =
        (boolean) HealthController.class.getMethod(funcToCall, JsonObject.class).invoke(null, body);

    if (!result) {
      throw new BadRequestException("Failed to execute function.");
    }

    JsonObject response = new JsonObject();
    response.addProperty("response", "Function executed.");

    ctx.writeAndFlush(response);
  }
}
