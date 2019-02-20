package me.mohamedelzarei.gitrekt.config;

import java.util.Properties;

public class Config {
  Properties configFile;

  /** Initialize and Load config file. */
  public Config() {
    configFile = new Properties();
    try {
      configFile.load(getClass().getClassLoader().getResourceAsStream("config.cfg"));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public String getProperty(String key) {
    return this.configFile.getProperty(key);
  }

  private static class ConfigHelper {
    private static final Config INSTANCE = new Config();
  }

  public static final Config instance = new Config();

  public static Config getInstance() {
    return ConfigHelper.INSTANCE;
  }
}
