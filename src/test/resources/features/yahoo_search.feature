Feature: Yahoo Search Functionality

  As a user, I want to search on Yahoo
  So that I can find relevant information.

  Scenario: User searches for a term on Yahoo
    Given I am on the Yahoo homepage
    When I search for "Selenium WebDriver"
    Then the search results page title should contain "Selenium WebDriver"
