package me.mohamedelzarei.gitrekt.config;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Config {
    Properties configFile;

    public Config() {
        configFile = new Properties();
        try {
            configFile.load(new FileReader(new File("resources/config.cfg").getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
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
