package ui.web.taf.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.web.taf.utils.ConfigUtils;
import ui.web.taf.utils.LoggingUtils;
import ui.web.taf.utils.ScreenshotUtils;
import ui.web.taf.utils.WaitUtils;

public class YahooSearchPage {
    private WebDriver driver;
    private int explicitWaitTime;

    // Yahoo search box locator (name="p")
    private By searchBoxLocator = By.name("p");

    public YahooSearchPage(WebDriver driver) {
        this.driver = driver;
        this.explicitWaitTime = ConfigUtils.getIntProperty("explicit.wait");
    }

    @Step("Navigate to Yahoo Homepage")
    public void navigateTo() {
        String url = ConfigUtils.getProperty("url");
        LoggingUtils.info("Navigating to URL: " + url);
        driver.get(url);
        ScreenshotUtils.captureFullPage(driver, "Homepage Loaded");
    }

    @Step("Search for text: {0}")
    public void searchFor(String text) {
        LoggingUtils.info("Searching for text: " + text);
        WebElement searchBox = WaitUtils.waitForElementToBeVisible(driver, searchBoxLocator, explicitWaitTime);
        
        ScreenshotUtils.captureElement(searchBox, "Search Box - Before Interaction");
        
        searchBox.clear();
        LoggingUtils.debug("Cleared search box.");
        ScreenshotUtils.captureElement(searchBox, "Search Box - After Clear");

        searchBox.sendKeys(text);
        LoggingUtils.debug("Entered text into search box.");
        ScreenshotUtils.captureElement(searchBox, "Search Box - After Entering Text: " + text);

        searchBox.sendKeys(Keys.RETURN);
        LoggingUtils.debug("Pressed RETURN key.");
        // Note: Not capturing screenshot after RETURN as it triggers navigation and element might become stale
    }

    @Step("Get Page Title")
    public String getTitle() {
        String title = driver.getTitle();
        LoggingUtils.info("Retrieved page title: " + title);
        return title;
    }
}
