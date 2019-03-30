package com.gitrekt.quora.controller;

import com.gitrekt.quora.config.Config;
import com.gitrekt.quora.database.postgres.PostgresConnection;
import com.gitrekt.quora.pooling.ThreadPool;
import com.gitrekt.quora.queue.MessageQueueConnection;
import com.gitrekt.quora.queue.MessageQueueConsumer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class HealthController {

  /** Set Maximum thread count for workers. */
  public static boolean setThreadPoolCount(JsonObject body) {
    JsonElement element = body.get("thread_pool_count");
    if (element.isJsonNull()) {
      return false;
    }
    int threadPoolCount;

    try {
      threadPoolCount = element.getAsInt();
      ThreadPool.getInstance().setNumOfThreads(threadPoolCount);
      return true;
    } catch (ClassCastException exception) {
      return false;
    }
  }

  /** Set Maximum thread count for postgres. */
  public static boolean setPgPoolCount(JsonObject body) {
    JsonElement element = body.get("pg_pool_count");
    if (element.isJsonNull()) {
      return false;
    }
    int dbPoolCount;

    try {
      dbPoolCount = element.getAsInt();
      PostgresConnection.getInstance().dataSource.setMaximumPoolSize(dbPoolCount);
    } catch (ClassCastException exception) {
      return false;
    }
    return true;
  }

  /** Set error reporting level for the logger. */
  public static boolean setErrorReportingLevel(JsonObject body) {
    boolean hasCorrectArgs = hasArgs(body, "log_level");
    if (!hasCorrectArgs) {
      return false;
    }
    int logLevel;

    try {
      logLevel = body.get("log_level").getAsInt();
    } catch (ClassCastException exception) {
      return false;
    }
    return true;
  }

  /** Freeze the service. */
  public static boolean freezeService(JsonObject body) {
    PostgresConnection.getInstance().closeConnection();
    try {
      MessageQueueConnection.getInstance().closeConnection();
    } catch (IOException exception) {
      return false;
    }
    return true;
  }

  /** Continue using the service. */
  public static boolean continueService(JsonObject body) {
    MessageQueueConnection.getInstance().connect();
    try {
      new MessageQueueConsumer();
    } catch (IOException exception) {
      return false;
    }
    return true;
  }

  /**
   * Create a new command from data. Expected Format: { "function_name" : "add_command",
   * "command_name" : "Example", "command_data" : "BASE64 ENCODED DATA FOR .class file" }
   */
  public static boolean addCommand(JsonObject body) {
    boolean hasCorrectArgs = hasArgs(body, "command_name", "command_data");
    if (!hasCorrectArgs) {
      return false;
    }

    String commandName = body.get("command_name").getAsString();
    String commandData = body.get("command_data").getAsString();

    return addClass(commandName, getPathFromCommandName(commandName), commandData, true);
  }

  /**
   * Update existing command. Expected Format: { "function_name" : "update_command", "command_name"
   * : "Example", "command_data" : "BASE64 ENCODED DATA FOR .class file" }
   */
  public static boolean updateCommand(JsonObject body) {
    return addCommand(body);
  }

  /**
   * Delete existing command. Expected Format: { "function_name" : "delete_command", "command_name"
   * : "Example" }
   */
  public static boolean deleteCommand(JsonObject body) {
    boolean hasCorrectArgs = hasArgs(body, "command_name");
    if (!hasCorrectArgs) {
      return false;
    }

    String commandName = body.get("command_name").getAsString();
    try {
      deleteClass(commandName, getPathFromCommandName(commandName));
    } catch (IOException exception) {
      return false;
    }
    return true;
  }

  /**
   * Create/Update Existing class. Expected Format: { "function_name" : "update_class", "class_path"
   * : "com.gitrekt.quora.models.Request", "class_data" : "BASE64 ENCODED DATA FOR .class file" }
   */
  public static boolean updateClass(JsonObject body) {
    boolean hasCorrectArgs = hasArgs(body, "class_path", "class_data");
    if (!hasCorrectArgs) {
      return false;
    }

    String classData = body.get("class_data").getAsString();
    String filePath = body.get("class_path").getAsString().replaceAll("\\.", "/");
    filePath = Config.getInstance().getProperty("classpath") + filePath + ".class";

    return addClass("", filePath, classData, false);
  }

  /** Create a new class. */
  private static boolean addClass(
      String className, String classPath, String classData, boolean updateConfig) {
    byte[] data = Base64.getDecoder().decode(classData);
    try {
      writeClass(className, classPath, updateConfig, data);
    } catch (IOException exception) {
      return false;
    }
    return true;
  }

  /** Return the path for a certain command. */
  private static String getPathFromCommandName(String name) {
    String filePath = Config.getInstance().getProperty("commands") + "." + name + "Command";
    filePath = filePath.replaceAll("\\.", "/");
    return Config.getInstance().getProperty("classpath") + filePath + ".class";
  }

  /** Check that JSON has all required args. */
  private static boolean hasArgs(JsonObject body, String... args) {
    for (String arg : args) {
      if (body.get(arg).isJsonNull()) {
        return false;
      }
    }
    return true;
  }

  /** Delete Class File. */
  private static void deleteClass(String name, String path) throws IOException {
    Files.deleteIfExists(Paths.get(path));
    Config.getInstance().deleteProperty(name + System.getenv("SERVICE_PREFIX"));
  }

  /** Convert Hex string to byte file. */
  private static byte[] hexStringToByteArray(String string) {
    byte[] bytes = new byte[string.length() / 2];
    for (int i = 0; i < bytes.length; i++) {
      int index = i * 2;
      int val = Integer.parseInt(string.substring(index, index + 2), 16);
      bytes[i] = (byte) val;
    }
    return bytes;
  }

  /** Write class to file. */
  private static void writeClass(String name, String path, boolean updateConfig, byte[] contents)
      throws IOException {
    Files.deleteIfExists(Paths.get(path));

    String hexString = new String(contents).replaceAll("\\s+", "");

    OutputStream fileOutputStream = new FileOutputStream(path);
    fileOutputStream.write(hexStringToByteArray(hexString));
    fileOutputStream.close();

    if (updateConfig) {
      String key = name.toLowerCase() + System.getenv("SERVICE_PREFIX");
      Config.getInstance().setProperty(key, name.toLowerCase() + "Command");
    }
  }
}
