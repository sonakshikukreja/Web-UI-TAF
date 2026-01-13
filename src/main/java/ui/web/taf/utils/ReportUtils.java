package ui.web.taf.utils;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

public class ReportUtils {

    public static void attachScreenshot(String name, byte[] screenshot) {
        if (screenshot != null && screenshot.length > 0) {
            LoggingUtils.debug("Attaching screenshot to Allure report: " + name);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        } else {
            LoggingUtils.warn("Attempted to attach empty screenshot: " + name);
        }
    }

    public static void logStep(String message) {
        LoggingUtils.info("Allure Step: " + message);
        Allure.step(message);
    }
}
