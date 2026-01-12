package ui.web.taf.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    }

    @Step("Search for text: {0}")
    public void searchFor(String text) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));
        searchBox.clear();
        searchBox.sendKeys(text);
        searchBox.sendKeys(Keys.RETURN);
    }

    @Step("Get Page Title")
    public String getTitle() {
        return driver.getTitle();
    }
}