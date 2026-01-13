package ui.web.taf.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ui.web.taf.pages.YahooSearchPage;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class YahooSearchStepDefs {
    private WebDriver driver;
    private YahooSearchPage yahooSearchPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        yahooSearchPage = new YahooSearchPage(driver);
    }

    @Given("I am on the Yahoo homepage")
    public void i_am_on_the_yahoo_homepage() {
        yahooSearchPage.navigateTo();
    }

    @When("I search for {string}")
    public void i_search_for(String searchTerm) {
        String originalWindow = driver.getWindowHandle();
        yahooSearchPage.searchFor(searchTerm);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    @Then("the search results page title should contain {string}")
    public void the_search_results_page_title_should_contain(String searchTerm) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains(searchTerm));

        String title = yahooSearchPage.getTitle();
        Assert.assertTrue(title.contains(searchTerm), "Title should contain the search term");

        // Capture and attach full-page screenshot after assertion
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("After Assertion - Full Page", new ByteArrayInputStream(screenshot));
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Capture screenshot on failure
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Test Failure Screenshot", new ByteArrayInputStream(screenshot));
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
