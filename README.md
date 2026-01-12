# Web UI Test Automation Framework (TAF) with BDD

This project is a Selenium-based test automation framework designed to test Yahoo Search functionality. It uses Java, Maven, TestNG, Cucumber for BDD, and Allure for reporting.

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
│   │           └── pages/
│   │               └── YahooSearchPage.java  # Page Object Model for Yahoo Search
│   └── test/
│       ├── java/
│       │   └── ui/web/taf/
│       │       ├── runner/
│       │       │   └── TestRunner.java       # Cucumber TestNG Runner
│       │       └── stepdefs/
│       │           └── YahooSearchStepDefs.java # Step Definitions
│       └── resources/
│           └── features/
│               └── yahoo_search.feature    # Cucumber Feature File
```

## Components

### 1. BDD Layer
- **`yahoo_search.feature`**: A plain-text file written in Gherkin that describes the test scenario.
- **`YahooSearchStepDefs.java`**: The Java implementation of the Gherkin steps. This class contains the core test logic and uses the Page Object Model.
- **`TestRunner.java`**: A TestNG class that runs the Cucumber features.

### 2. Page Object Model (`YahooSearchPage.java`)
- Encapsulates the mechanics of the Yahoo Search page.

### 3. Dependencies (`pom.xml`)
- **Cucumber**: For BDD capabilities.
- **Allure Cucumber**: For integrating Allure reports with Cucumber.

## How to Execute Tests

### Option 1: Using Maven Command Line
Run the tests from the project root:
```bash
mvn clean test
```

### Option 2: Using IDE
1.  Open `src/test/java/ui/web/taf/runner/TestRunner.java`.
2.  Right-click on the `TestRunner` class.
3.  Select **Run 'TestRunner'**.

## Generating and Viewing Allure Reports

After running the tests, Allure results are generated in the `target/allure-results` directory. To view the report:

1.  **Install Allure Commandline** (if not already installed).
2.  **Serve the Report**:
    ```bash
    allure serve target/allure-results
    ```
This will start a local web server and open the report in your default browser.
