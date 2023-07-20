# Assurity API Automation Assignment

## Project Overview

The "Assurity API Automation Assignment" project aims to automate testing for a specific API using HTTPClient. The goal is to perform tests to check if the API meets certain requirements.

## API

API Endpoint: [https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false](https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false)

## Acceptance Criteria

Given the API endpoint mentioned above, the API should meet the following acceptance criteria:

- The API response should contain a product with the name "Carbon credits."
- The product should have the property "CanRelist" set to true.
- There should be a promotion named "Gallery" with a description containing the text "Good position in category."

## How It Works

This project uses Cucumber, a testing framework that makes test scenarios easy to read for both technical and non-technical users. The tests are written in Java, a widely-used programming language.

## Installation

Before you can run the tests, you need to install a few things:

- **Java Development Environment:**
  Install a Java Integrated Development Environment (IDE) like Eclipse or IntelliJ to work with Java code.

- **Maven:**
  Install Maven, a tool that helps manage the project and its dependencies. You can download and install Maven from [here](https://maven.apache.org/install.html).

- **Cucumber:**
  Cucumber is a special tool used for testing in a natural language format. No need to install it separately; it comes bundled with the project.

## Code-Level Components and Configurations

### Feature File: `src/test/resources/features/assurity_api.feature`

The feature file contains the Cucumber scenarios written in a human-readable format using Gherkin language. It defines the acceptance criteria and expected behaviors of the API.

### POM.xml

The `pom.xml` file is the Project Object Model (POM) for the project. It manages project configurations, dependencies, and build settings. Here's a summary of the key dependencies used in the project:

```xml
<!-- Cucumber Java -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.13.0</version>
</dependency>
<!-- Cucumber BDD framework for Java -->

<!-- Cucumber JUnit -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.13.0</version>
    <scope>test</scope>
</dependency>
<!-- Cucumber JUnit integration -->

<!-- SnakeYAML -->
<dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
    <version>2.0</version>
</dependency>
<!-- Library for working with YAML files -->

<!-- Apache HttpClient 5 -->
<dependency>
    <groupId>org.apache.httpcomponents.client5</groupId>
    <artifactId>httpclient5</artifactId>
    <version>5.2.1</version>
</dependency>
<!-- HTTP client library for making API requests -->

<!-- Gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
<!-- Library for working with JSON data -->

<!-- SLF4J API -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.7</version>
</dependency>
<!-- Logging API for the Simple Logging Facade for Java -->

<!-- Logback Classic -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.8</version>
    <scope>test</scope>
</dependency>
<!-- Implementation of SLF4J for logging -->

<!-- ExtentReports Cucumber7 Adapter -->
<dependency>
    <groupId>tech.grasshopper</groupId>
    <artifactId>extentreports-cucumber7-adapter</artifactId>
    <version>1.13.0</version>
</dependency>
<!-- Adapter to integrate ExtentReports with Cucumber 7 -->

## AssertionHelper

The `AssertionHelper` class contains a custom assertion method called `asserter`, which is used to check if a given condition is true. If the condition is false, an assertion error will be thrown.

## HttpApiProcessor

The `HttpApiProcessor` class handles HTTP requests and responses for API testing. It utilizes HttpClient to perform GET requests, retrieve endpoint details from the configuration, parse and pretty-print JSON responses, and store the formatted JSON for further validations.

## LogPrinter

The `LogPrinter` class provides utility methods to log API testing information and validation results.

## ResponseValidator

The `ResponseValidator` class provides methods to validate fields in an API response and perform data validation based on the provided DataTable.

## Configuration Files

- **Application-test.yml:** The `application-test.yml` file contains API configuration settings required for testing. This includes defining endpoint URLs, timeouts, and other necessary properties.

- **extent.properties:** The `extent.properties` file contains configuration settings for ExtentReports, a reporting library. It allows you to customize the appearance and behavior of the test reports.

- **logback.xml:** The `logback.xml` file contains the logging configuration for the project. It specifies log output formats, log levels, and log file locations. The logger outputs a log file in the logs folder.

## ApiGetRequestValidationSteps

The `ApiGetRequestValidationSteps` class contains step definitions for the API validation scenarios.

## Running the Tests

Follow these steps to run the tests:

1. **Clone the Project:**
   First, make a copy of the project on your computer by "cloning" it. You can do this by running the following command in the terminal or command prompt:
   ```bash
   git clone https://github.com/your-username/assurity-api-automation-assignment.git

