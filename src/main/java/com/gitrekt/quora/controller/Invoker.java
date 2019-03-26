package com.gitrekt.quora.controller;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.handlers.PostgresHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;

// Would be better to store all commands in a Map commandName -> Command
public final class Invoker {

  public static Object invoke(String commandName, HashMap<String, Object> arguments)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
          InvocationTargetException, InstantiationException, SQLException {
    // Use config to get the command name.
    //    String commandName = "";
    //    String commandPackageName = "";
    String commandClassPath = Command.class.getPackage().getName() + ".handlers." + commandName;
    Class<?> commandClass = getClass(commandClassPath);
    Constructor<?> commandConstructor = getConstructor(commandClass, HashMap.class);

    //    String postgresPackageName = "";
    String postgresClassPath =
        PostgresHandler.class.getPackage().getName() + ".UsersPostgresHandler";
    Constructor<?> postgresConstructor = getConstructor(getClass(postgresClassPath));

    Command command = (Command) commandConstructor.newInstance(arguments);

    //    command.setHandler();
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
