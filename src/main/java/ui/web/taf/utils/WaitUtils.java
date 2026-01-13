package ui.web.taf.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class WaitUtils {

    // --- Implicit Wait ---
    public static void setImplicitWait(WebDriver driver, int seconds) {
        LoggingUtils.debug("Setting implicit wait to " + seconds + " seconds.");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    // --- Explicit Waits ---
    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        LoggingUtils.debug("Waiting for element to be visible: " + locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        LoggingUtils.debug("Waiting for element to be clickable: " + locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForTitleToContain(WebDriver driver, String title, int timeoutInSeconds) {
        LoggingUtils.debug("Waiting for title to contain: " + title);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.titleContains(title));
    }

    public static boolean waitForNumberOfWindowsToBe(WebDriver driver, int count, int timeoutInSeconds) {
        LoggingUtils.debug("Waiting for number of windows to be: " + count);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(count));
    }

    public static boolean waitForElementToDisappear(WebDriver driver, By locator, int timeoutInSeconds) {
        LoggingUtils.debug("Waiting for element to disappear: " + locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForAlertToBePresent(WebDriver driver, int timeoutInSeconds) {
        LoggingUtils.debug("Waiting for alert to be present.");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.alertIsPresent());
    }

    // --- Fluent Waits ---
    public static <T> T fluentWait(WebDriver driver, Function<WebDriver, T> condition, int timeoutInSeconds, int pollingInMillis) {
        LoggingUtils.debug("Starting fluent wait with timeout: " + timeoutInSeconds + "s, polling: " + pollingInMillis + "ms");
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(pollingInMillis))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        return wait.until(condition);
    }

    public static WebElement waitForElementVisibleFluent(WebDriver driver, By locator, int timeoutInSeconds, int pollingInMillis) {
        return fluentWait(driver, ExpectedConditions.visibilityOfElementLocated(locator), timeoutInSeconds, pollingInMillis);
    }

    public static WebElement waitForElementClickableFluent(WebDriver driver, By locator, int timeoutInSeconds, int pollingInMillis) {
        return fluentWait(driver, ExpectedConditions.elementToBeClickable(locator), timeoutInSeconds, pollingInMillis);
    }

    // --- Hard Coded Sleep ---
    public static void sleep(int seconds) {
        try {
            LoggingUtils.warn("Sleeping for " + seconds + " seconds.");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggingUtils.error("Thread sleep interrupted: " + e.getMessage());
        }
    }

    public static void sleepInMillis(long millis) {
        try {
            LoggingUtils.warn("Sleeping for " + millis + " milliseconds.");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggingUtils.error("Thread sleep interrupted: " + e.getMessage());
        }
    }
}
