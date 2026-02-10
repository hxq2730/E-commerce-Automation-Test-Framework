# 🚀 Selenium Automation Framework

![Java](https://img.shields.io/badge/Java-17-orange) ![Selenium](https://img.shields.io/badge/Selenium-4.38-green) ![TestNG](https://img.shields.io/badge/TestNG-7.10-blue) ![Rest-Assured](https://img.shields.io/badge/Rest_Assured-5.3-purple)

## 📖 Introduction

This repository contains a robust **Hybrid Automation Testing Framework** designed to test the **CMS Anh Tester** e-commerce platform. The framework is built using **Java**, combining **Selenium WebDriver** for UI testing and **Rest-Assured** for API testing, and **TestNG**, following the **Page Object Model (POM)** design pattern for maintainability and scalability.

It integrates **Data Driven Testing (DDT)** using Excel files and generates detailed **Allure Reports**. The project is fully automated with **CI/CD via GitHub Actions**.

## 🛠 Tech Stack & Tools

-   **Language:** Java (JDK 17+)
-   **UI Automation:** Selenium WebDriver
-   **API Automation:** Rest-Assured
-   **Test Runner:** TestNG
-   **Build Tool:** Maven
- **JSON Parsing:** Jackson Databind / GSON
-   **Reporting:** Allure Report
-   **Data Handling:** Apache POI (Excel)
-   **Logging:** Log4j2
-   **CI/CD:** GitHub Actions

## ✨ Key Features

-   **Hybrid Testing:** Seamless integration of UI (Frontend) and API (Backend) testing in one project.
-   **API Coverage:** Comprehensive CRUD testing for Auth, Products, Cart, and Orders using POJO classes.
-   **Page Object Model (POM):** Clean separation between test logic and UI objects.
-   **Data Driven Testing:** Run tests with multiple data sets from Excel files.
-   **Cross-Browser Testing:** Supports Chrome, Edge, and Firefox.
-   **Parallel Execution:** Configured to run tests in parallel to reduce execution time.
-   **Dynamic Configuration:** Run tests via Maven Profiles (API-only, UI-only, or Regression).
-   **Headless Execution:** Optimized for CI/CD environments with auto-screen resolution handling.
-   **Automatic Reporting:** Generates Allure HTML reports and deploys to GitHub Pages.

## 📂 Project Structure

```text
src
├── main
│   └── java
│       └── org.example
│           ├── api         # API Endpoints (AuthApi, CartApi, ProductApi...)
│           ├── constants   # FrameworkConstants
│           ├── driver      # DriverManager (Singleton & ThreadLocal)
│           ├── helpers     # ExcelHelper, CaptureHelper...
│           ├── models      # POJO classes for API responses
│           ├── pages       # Page Classes (POM)
│           └── utils       # LogUtils
└── test
    ├── java
    │   └── org.example    │       
    │       ├── base        # BaseTest
    │       ├── listeners   # Test Listener
    │       └── tests       # Test Classes (LoginTest, CheckoutTest...)
    │           ├── api         # API Test Classes (CartApiTest, OrderApiTest...)
    │           └── ui          # UI Test Classes (LoginTest, CheckoutTest...)
    └── resources
        ├── config          # config.properties
        ├── suites          # TestNG XML Suites
        ├── testdata        # Excel Data & Upload Files
        ├── allure.properties
        └── log4j2.xml
```

## ⚙️ Prerequisites

Before running the tests, ensure you have the following installed:

-   [Java Development Kit (JDK) 17+](https://adoptium.net/)
-   [Maven](https://maven.apache.org/)
-   IntelliJ IDEA or Eclipse

## 🚀 How to Run Tests

### 1. Clone the repository

```bash
https://github.com/hxq2730/E-commerce-Automation-Test-Framework.git
cd E-commerce-Automation-Test-Framework
```

### 2. Run API Tests Only

Fast execution to verify backend stability.

```bash
mvn clean test -Papi
```
### 3. Run UI Tests Only
End-to-End user flow testing.

```bash
mvn clean test -Pui
```

### 4. Run Full Regression (API + UI)

```bash
mvn clean test -Pregression
```
## 📊 Test Reports

### Local Report

After the test execution is complete, generate and open the Allure report locally:

```bash
mvn allure:serve
```

### 🌐 Live CI/CD Report

The latest test run report is automatically deployed to GitHub Pages via GitHub Actions workflow. 👉 **[Click here to view Live Allure Report](https://hxq2730.github.io/E-commerce-Automation-Test-Framework/)**

## 🤖 CI/CD Pipeline

This project uses **GitHub Actions** for Continuous Integration:

1.  **Trigger:** Pushes to `main` branch or Scheduled daily at 7:00 AM (GMT+7).
2.  **Environment:** Ubuntu Latest + Java 17 + Chrome (Headless).
3.  **Stage 1 - API Tests:** Runs testng-api.xml.
3.  **Stage 2 - UI Tests:** Runs testng-ui.xml (Headless Chrome).
4.  **Reporting:** Generates Allure HTML report.
5.  **Deployment:** Deploys the report to the `gh-pages` branch.

## 👤 Author

**Ho Xuan Quang**

-   GitHub: @hxq2730 ([https://github.com/hxq2730](https://github.com/hxq2730)
-   Email: [hoxuanquangqt@gmail.com](mailto:hoxuanquangqt@gmail.com)]
