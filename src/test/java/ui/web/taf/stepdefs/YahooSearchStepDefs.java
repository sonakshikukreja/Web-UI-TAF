package ui.web.taf.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import ui.web.taf.pages.YahooSearchPage;
import ui.web.taf.utils.ConfigUtils;
import ui.web.taf.utils.LoggingUtils;
import ui.web.taf.utils.ScreenshotUtils;
import ui.web.taf.utils.WaitUtils;

public class YahooSearchStepDefs {
    private WebDriver driver;
    private YahooSearchPage yahooSearchPage;
    private int explicitWaitTime;

    @Before
    public void setUp(Scenario scenario) {
        // Configure logger with the feature name (extracted from scenario URI or just hardcoded for now)
        // Ideally, we'd parse the feature name, but for simplicity, we'll use "YahooSearch" or extract from scenario
        String featureName = "YahooSearch"; 
        // Attempt to get feature name from scenario ID if possible, or just use a clean name
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
        
        // Using a combination or just the feature name as requested
        LoggingUtils.configureLogger(featureName);

        LoggingUtils.info("Starting scenario: " + scenario.getName());
        try {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            
            int implicitWaitTime = ConfigUtils.getIntProperty("implicit.wait");
            explicitWaitTime = ConfigUtils.getIntProperty("explicit.wait");
            
            WaitUtils.setImplicitWait(driver, implicitWaitTime);
            yahooSearchPage = new YahooSearchPage(driver);
            LoggingUtils.info("Browser initialized successfully.");
        } catch (Exception e) {
            LoggingUtils.error("Failed to initialize browser: " + e.getMessage());
            throw e;
        }
    }

    @Given("I am on the Yahoo homepage")
    public void i_am_on_the_yahoo_homepage() {
        LoggingUtils.info("Step: I am on the Yahoo homepage");
        yahooSearchPage.navigateTo();
    }

    @When("I search for {string}")
    public void i_search_for(String searchTerm) {
        LoggingUtils.info("Step: I search for '" + searchTerm + "'");
        String originalWindow = driver.getWindowHandle();
        yahooSearchPage.searchFor(searchTerm);

        LoggingUtils.info("Waiting for new window to open...");
        WaitUtils.waitForNumberOfWindowsToBe(driver, 2, explicitWaitTime);

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                LoggingUtils.info("Switched to new window: " + windowHandle);
                break;
            }
        }
    }

    @Then("the search results page title should contain {string}")
    public void the_search_results_page_title_should_contain(String searchTerm) {
        LoggingUtils.info("Step: the search results page title should contain '" + searchTerm + "'");
        WaitUtils.waitForTitleToContain(driver, searchTerm, explicitWaitTime);

        String title = yahooSearchPage.getTitle();
        LoggingUtils.info("Current page title: " + title);
        
        try {
            Assert.assertTrue(title.contains(searchTerm), "Title should contain the search term");
            LoggingUtils.info("Assertion Passed: Title contains '" + searchTerm + "'");
        } catch (AssertionError e) {
            LoggingUtils.error("Assertion Failed: " + e.getMessage());
            throw e;
        }

        // Capture and attach full-page screenshot after assertion
        ScreenshotUtils.captureFullPage(driver, "After Assertion - Full Page");
    }

    @After
    public void tearDown(Scenario scenario) {
        LoggingUtils.info("Finishing scenario: " + scenario.getName());
        if (scenario.isFailed()) {
            LoggingUtils.error("Scenario failed! Capturing failure screenshot.");
            // Capture screenshot on failure
            ScreenshotUtils.captureFullPage(driver, "Test Failure Screenshot");
        }
        if (driver != null) {
            driver.quit();
            LoggingUtils.info("Browser closed.");
        }
    }
}
