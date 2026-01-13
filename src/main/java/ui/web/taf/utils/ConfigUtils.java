package ui.web.taf.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
    private static Properties properties;
    private static final String DEFAULT_CONFIG_FILE = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        // Check if a specific environment config is requested via system property (e.g., -Denv=qa)
        String env = System.getProperty("env");
        String configFilePath = DEFAULT_CONFIG_FILE;

        if (env != null && !env.isEmpty()) {
            configFilePath = "src/test/resources/config-" + env + ".properties";
            LoggingUtils.info("Loading configuration for environment: " + env);
        } else {
            LoggingUtils.info("Loading default configuration.");
        }

        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
        } catch (IOException e) {
            LoggingUtils.error("Failed to load configuration file: " + configFilePath + ". Error: " + e.getMessage());
            throw new RuntimeException("Configuration load failed", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            LoggingUtils.warn("Property '" + key + "' not found in configuration.");
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            LoggingUtils.error("Property '" + key + "' is not a valid integer. Value: " + value);
            throw e;
        }
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
}
