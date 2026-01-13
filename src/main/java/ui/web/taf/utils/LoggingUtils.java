package ui.web.taf.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingUtils {
    private static Logger logger;
    private static FileHandler fileHandler;

    // Initialize logger (default to console if not configured)
    static {
        logger = Logger.getLogger("TAF_Logger");
        logger.setLevel(Level.ALL);
    }

    public static void configureLogger(String featureName) {
        try {
            // Create logs directory if it doesn't exist
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Generate timestamp
            String timestamp = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
            String fileName = "logs/TestRun_" + featureName + "_" + timestamp + ".log";

            // Close previous handler if exists to avoid locking files
            if (fileHandler != null) {
                logger.removeHandler(fileHandler);
                fileHandler.close();
            }

            // Configure FileHandler
            fileHandler = new FileHandler(fileName, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            info("Logger configured. Writing to file: " + fileName);

        } catch (IOException e) {
            System.err.println("Failed to configure file logger: " + e.getMessage());
        }
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.severe(message);
    }

    public static void warn(String message) {
        logger.warning(message);
    }

    public static void debug(String message) {
        // JUL doesn't have "debug", mapping to FINE
        logger.fine(message);
    }
}
