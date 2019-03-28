package com.gitrekt.quora.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {

  private static final Logger LOGGER = Logger.getLogger(Config.class.getName());

  private static Config instance;

  static {
    try {
      instance = new Config();
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  /** Properties Map. */
  private Properties properties;

  private Config() throws IOException {
    properties = new Properties();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.cfg");
    if (inputStream == null) {
      throw new IOException("File Not Found!");
    }
    properties.load(inputStream);
  }

  /**
   * Returns the property mapped by @code name or null if it does not exist.
   *
   * @param name The property name
   * @return The result or null
   */
  public String getProperty(String name) {
    return this.properties.getProperty(name);
  }

  /**
   * Returns the Singleton Instance.
   *
   * @return The Configuration Instance
   */
  public static Config getInstance() {
    return instance;
  }
}
