package com.gitrekt.quora.controller;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.config.Config;
import com.gitrekt.quora.exceptions.ServerException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;

// Would be better to store all commands in a Map commandName -> Command
public final class Invoker {

  private static final Config CONFIG = Config.getInstance();

  /**
   * Run the command using reflection.
   */
  public static Object invoke(String commandName, HashMap<String, Object> arguments)
          throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
          InvocationTargetException, InstantiationException, SQLException, ServerException {

    String commandClassPath =
        CONFIG.getProperty("commands") + "." + CONFIG.getProperty(commandName);
    Class<?> commandClass = getClass(commandClassPath);
    Constructor<?> commandConstructor = getConstructor(commandClass, HashMap.class);

    //  String postgresClassPath = CONFIG.getProperty("postgres") + ".UsersPostgresHandler";
    //  Constructor<?> postgresConstructor = getConstructor(getClass(postgresClassPath));

    Command command = (Command) commandConstructor.newInstance(arguments);

    //  command.setPostgresHandler((UsersPostgresHandler) postgresConstructor.newInstance());

    return command.execute();
  }

  private static Class<?> getClass(String classPath) throws ClassNotFoundException {
    return Class.forName(classPath);
  }

  private static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes)
      throws NoSuchMethodException {

    return clazz.getConstructor(parameterTypes);
  }
}
