package com.gitrekt.quora.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {

  private static final Logger LOGGER = Logger.getLogger(Config.class.getName());

  private static Config instance;

  private Properties properties;

  private Config() throws IOException {
    properties = new Properties();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.cfg");
    if (inputStream == null) {
      throw new IOException("File Not Found!");
    }
    properties.load(inputStream);
  }

  public String getProperty(String name) {
    return this.properties.getProperty(name);
  }

  public static Config getInstance() throws IOException {
    if (instance != null) {
      return instance;
    }
    synchronized (Config.class) {
      if (instance == null) {
        instance = new Config();
      }
    }
    return instance;
  }
}
