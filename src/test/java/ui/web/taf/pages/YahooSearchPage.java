package ui.web.taf.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class YahooSearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Yahoo search box locator (name="p")
    private By searchBoxLocator = By.name("p");

    public YahooSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Navigate to Yahoo Homepage")
    public void navigateTo() {
        driver.get("https://www.yahoo.com");
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Homepage Loaded", new ByteArrayInputStream(screenshot));
    }

    @Step("Search for text: {0}")
    public void searchFor(String text) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));
        
        captureElementScreenshot(searchBox, "Search Box - Before Interaction");
        
        searchBox.clear();
        captureElementScreenshot(searchBox, "Search Box - After Clear");

        searchBox.sendKeys(text);
        captureElementScreenshot(searchBox, "Search Box - After Entering Text: " + text);

        searchBox.sendKeys(Keys.RETURN);
        // Note: Not capturing screenshot after RETURN as it triggers navigation and element might become stale
    }

    @Step("Get Page Title")
    public String getTitle() {
        return driver.getTitle();
    }

    private void captureElementScreenshot(WebElement element, String description) {
        try {
            byte[] elementScreenshot = element.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(description, new ByteArrayInputStream(elementScreenshot));
        } catch (Exception e) {
            System.err.println("Failed to capture element screenshot: " + e.getMessage());
        }
    }
}
