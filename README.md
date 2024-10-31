# Test Automation Project

This repository contains an automated test suite developed in Java, using Cucumber, Selenium, and JUnit to test web application functionalities.

## Project Structure

- **Java**: Primary language for test development.
- **Cucumber**: Tool for writing scenarios in Gherkin and executing BDD tests.
- **Selenium WebDriver**: Enables browser interaction to simulate user actions.
- **JUnit**: Test framework used for running and organizing tests.
- **Extent Reports**: Generates test reports for tracking test outcomes.

## Prerequisites

To run this project, you need the following installed:

- Java Development Kit (JDK) 8 or higher
- Maven (for dependency management and test execution)
- Chrome or Firefox browser (depending on the chosen driver in the project)

## Environment Setup

1. Clone this repository:
    ```bash
    git clone https://github.com/leoautqa/automation_Java.git
    ```
2. Navigate to the project directory:
    ```bash
    cd <PROJECT_DIRECTORY>
    ```
3. Install dependencies:
    ```bash
    mvn install
    ```

## Running Tests

You can execute the tests using Maven commands. To run all tests:

```bash
mvn test
```

To run specific feature files, use the following command:

```bash
gmvn test -Dcucumber.options="src/test/resources/features/<feature_name>.feature"
```

## Reports

Extent Reports are generated after each test run, providing detailed insights into test execution results.

### Accessing the Report

1. After running the tests, navigate to the following path:

```bash
target/extent-reports/ExtentReport.html
```

2. Open the `ExtentReport.html` file in a browser to view the results.

## Project Structure Overview

- `src/test/java`: Contains step definitions and hooks.
- `src/test/resources/features`: Holds all feature files written in Gherkin.
- `src/main/java`: Contains helper classes, utilities, and configuration files.

## Customization

You can configure specific settings like the browser type and base URL in the `config.properties` file found in `src/main/resources`. Adjust these values as needed for different environments.

## Troubleshooting

### Common Issues

- Browser not launching: Verify the correct WebDriver version is in place and matches the browser version.
- Dependency issues: Run mvn clean install to ensure all dependencies are correctly installed.
