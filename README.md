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
    git clone <REPOSITORY_URL>
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
