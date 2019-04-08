package com.gitrekt.quora.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

  private static Config instance;

  static {
    try {
      instance = new Config();
    } catch (IOException exception) {
      throw new ExceptionInInitializerError(exception);
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
   * Set value of property.
   *
   * @param key key of the property
   * @param value value of the property
   */
  public void setProperty(String key, String value) throws IOException {
    this.properties.setProperty(key, value);
    writeConfigFile();
  }

  /** Delete property from config file. */
  public void deleteProperty(String key) throws IOException {
    this.properties.remove(key);
    writeConfigFile();
  }

  /**
   * Write the config file to the disk.
   */
  private void writeConfigFile() throws IOException {
    properties.store(
        new FileOutputStream(getClass().getClassLoader().getResource("config.cfg").getFile()),
        "Service Properties");
  }

  /**
   * Returns the Singleton Instance.
   * @return The Configuration Instance
   */
  public static Config getInstance() {
    return instance;
  }
}
