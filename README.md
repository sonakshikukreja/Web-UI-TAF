# Web UI Test Automation Framework (TAF)

This project is a comprehensive, enterprise-grade test automation framework designed for testing web applications. It leverages **Selenium WebDriver** for browser automation, **Cucumber** for Behavior-Driven Development (BDD), **TestNG** for test execution, and **Allure** for detailed reporting.

## Key Features

*   **BDD with Cucumber**: Write test scenarios in plain English (Gherkin) to bridge the gap between technical and non-technical stakeholders.
*   **Page Object Model (POM)**: Promotes code reusability and maintainability by separating test logic from page interaction logic.
*   **Robust Wait Strategies**: Includes a centralized `WaitUtils` class supporting Implicit, Explicit, and Fluent waits to handle dynamic web elements reliably.
*   **Comprehensive Reporting**: Integrated Allure reporting provides rich, interactive HTML reports with step-by-step logs and screenshots.
*   **Visual Debugging**: Automatically captures screenshots of specific elements during interactions and full-page screenshots upon assertion or failure.
*   **Environment Management**: Easily switch between environments (QA, Stage, Prod) using configuration files and system properties.
*   **Utility Library**: A suite of helper classes for common tasks like Logging, Date manipulation, CSV/Excel processing, and Database connectivity.

## Test Architecture Diagram

```mermaid
graph TD
    %% Define styles for nodes
    classDef tool fill:#e1f5fe,stroke:#0277bd,stroke-width:2px,color:#01579b
    classDef code fill:#fff9c4,stroke:#fbc02d,stroke-width:2px,color:#f57f17
    classDef browser fill:#fce4ec,stroke:#c2185b,stroke-width:2px,color:#880e4f
    classDef report fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#1b5e20
    classDef artifact fill:#f5f5f5,stroke:#616161,stroke-width:2px,stroke-dasharray: 5 5,color:#212121
    classDef bdd fill:#ede7f6,stroke:#5e35b1,stroke-width:2px,color:#311b92

    subgraph Execution ["🚀 BDD Test Execution Flow"]
        Maven("Maven"):::tool -->|Triggers| TestNG("TestNG"):::tool
        TestNG -->|Executes| Runner["TestRunner"]:::code
        Runner -->|Reads| Feature["yahoo_search.feature"]:::bdd
        Feature --matches--> StepDefs["YahooSearchStepDefs"]:::code
        StepDefs -->|Uses| POM["YahooSearchPage (POM)"]:::code
        StepDefs -->|Uses| Utils["Utils (Wait, Config, Log)"]:::code
        POM -->|Commands| WebDriver("Selenium WebDriver"):::tool
        WebDriver -->|Automates| Browser(("Chrome Browser")):::browser
    end

    subgraph Reporting ["📊 Reporting Flow"]
        Listener("Allure-Cucumber Listener"):::tool -.->|Hooks into| Runner
        Listener -->|Generates| Results[("allure-results (JSON)")]:::artifact
        CLI("Allure CLI"):::tool -->|Reads| Results
        CLI -->|Generates| Report["Allure HTML Report"]:::report
    end
```

## Project Structure

```
Web-UI-TAF/
├── pom.xml                 # Maven configuration and dependencies
├── README.md               # Project documentation
├── src/
│   ├── main/
│   │   └── java/
│   │       └── ui/web/taf/
│   │           ├── pages/
│   │           │   └── YahooSearchPage.java  # Page Object Model (Main)
│   │           └── utils/                    # Utility Classes
│   │               ├── ConfigUtils.java      # Environment Configuration
│   │               ├── WaitUtils.java        # Centralized Wait Logic
│   │               ├── ScreenshotUtils.java  # Screenshot Capture
│   │               ├── LoggingUtils.java     # Standardized Logging
│   │               ├── ReportUtils.java      # Allure Reporting Helpers
│   │               ├── DateUtils.java        # Date/Time Helpers
│   │               ├── CSVUtils.java         # CSV File Handling
│   │               ├── ExcelUtils.java       # Excel File Handling
│   │               └── DBUtils.java          # Database Connectivity
│   └── test/
│       ├── java/
│       │   └── ui/web/taf/
│       │       ├── runner/
│       │       │   └── TestRunner.java       # Cucumber TestNG Runner
│       │       ├── stepdefs/
│       │       │   └── YahooSearchStepDefs.java # Step Definitions
│       │       └── pages/
│       │           └── YahooSearchPage.java  # Page Object (Test Specific)
│       └── resources/
│           ├── features/
│           │   └── yahoo_search.feature      # Cucumber Feature File
│           └── config.properties             # Default Configuration
```

## Prerequisites

1.  **Java JDK**: JDK 17 or higher is recommended.
2.  **Maven**: Ensure Maven is installed and added to your system PATH.
3.  **Google Chrome**: The tests are configured to run on Chrome.
4.  **Allure Commandline**: Required to view the generated reports locally.

## Configuration

The framework uses `src/test/resources/config.properties` for default settings.

```properties
browser=chrome
url=https://www.yahoo.com
implicit.wait=10
explicit.wait=10
headless=false
```

To run against a different environment (e.g., QA), create a `config-qa.properties` file and run with `-Denv=qa`.

## How to Execute Tests

### Option 1: Using Maven Command Line

Run all tests:
```bash
mvn clean test
```

Run with a specific environment config:
```bash
mvn clean test -Denv=qa
```

### Option 2: Using IDE (IntelliJ / Android Studio)

1.  Navigate to `src/test/java/ui/web/taf/runner/TestRunner.java`.
2.  Right-click on the `TestRunner` class.
3.  Select **Run 'TestRunner'**.

## Generating and Viewing Reports

After test execution, Allure results are generated in `target/allure-results`.

To view the report, run:
```bash
allure serve target/allure-results
```
This command will start a local web server and automatically open the interactive report in your default browser.

## Troubleshooting

*   **Browser Driver Issues**: Selenium 4.6+ uses Selenium Manager to automatically handle drivers. If you face issues, ensure your browser is up-to-date.
*   **Plugin Errors**: If you see `Could not load plugin class`, ensure your `TestRunner` references `io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm`.
