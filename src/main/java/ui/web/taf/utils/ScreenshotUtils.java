package ui.web.taf.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScreenshotUtils {

    public static void captureFullPage(WebDriver driver, String description) {
        try {
            LoggingUtils.debug("Capturing full page screenshot: " + description);
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            ReportUtils.attachScreenshot(description, screenshot);
        } catch (Exception e) {
            LoggingUtils.error("Failed to capture full page screenshot: " + e.getMessage());
        }
    }

    public static void captureElement(WebElement element, String description) {
        try {
            LoggingUtils.debug("Capturing element screenshot: " + description);
            byte[] elementScreenshot = element.getScreenshotAs(OutputType.BYTES);
            ReportUtils.attachScreenshot(description, elementScreenshot);
        } catch (Exception e) {
            LoggingUtils.error("Failed to capture element screenshot: " + e.getMessage());
        }
    }
}
