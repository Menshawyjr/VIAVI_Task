🚀 Project Overview
This project implements a complete, robust, and scalable automation test suite for the Prestashop demo site using Java, Selenium WebDriver, and TestNG.
The architecture follows the Page Object Model (POM) pattern, employing advanced synchronization strategies to eliminate flakiness commonly found in introductory Selenium projects.

🏗️ Key Features and Architecture
Stale Element Prevention: All interaction methods (click, sendKeys) use By locators, preventing StaleElementReferenceException.
Centralized Configuration: All URLs, timeouts, and browser types are managed in Config.java (DRY principle).
Robust Driver Management: Uses WebDriverManager within a factory pattern (WebDriverFactory.java) to ensure the correct ChromeDriver is always used, avoiding manual driver setup and resolving CDP version mismatches.
Parameterized Assertions: Implements bulletproof, parameterized assertions (e.g., assertProductInCart(String name)) for clear, failure-reporting test verification.
Enterprise-Grade Structure: Clean separation of concerns with dedicated packages for pages, tests, utils, and configuration.

📋 Test Scenario Coverage
✅ Complete End-to-End Workflow:
Open PrestaShop Demo Site
Create User Account - Complete registration flow
Product Search - Search for "notebook" products
Product Verification - Select first result & assert image presence
Cart Management - Add product to cart and navigate to cart page
Order Verification - Assert product successfully added to cart

⚙️ Project Setup
Prerequisites
Java Development Kit (JDK): Version 11 or higher (Recommended: JDK 17 LTS)
Maven: Version 3.6 or higher
Chrome Browser: The latest stable version
IDE: IntelliJ IDEA or Eclipse (with Maven support)

1. Clone the Repository
bash
git clone https://github.com/your-username/VIAVI_Task.git
cd VIAVI_Task

3. Project Structure
VIAVI_Task/
├── src/test/java/
│   ├── config/
│   │   └── Config.java
│   ├── pages/
│   │   ├── BasePage.java
│   │   ├── HomePage.java
│   │   ├── LoginPage.java
│   │   ├── AuthenticationPage.java
│   │   ├── SearchPage.java
│   │   ├── ProductPage.java
│   │   └── CartPage.java
│   ├── tests/
│   │   └── PrestaShopTest.java
│   └── utils/
│       └── WebDriverFactory.java
├── pom.xml
├── testng.xml
└── README.md
4. Build and Run Tests
Compile the project:

bash
mvn clean compile
Run all tests:

bash
mvn test
Run with TestNG suite:

bash
mvn surefire:test
Run specific test class:

bash
mvn test -Dtest=PrestaShopTest
🛠️ Technical Implementation
Core Components
BasePage.java - Foundation class providing:

Advanced WebDriverWait synchronization

Robust element interaction methods

Stale element reference prevention

Common utility methods

WebDriverFactory.java - Factory pattern implementation:

Automatic browser driver management

Multi-browser support (Chrome, Firefox, Edge)

Headless mode configuration

Thread-safe driver instantiation

Config.java - Centralized configuration:

java
public class Config {
    public static final String BASE_URL = "https://demo.prestashop.com/";
    public static final int TIMEOUT = 10;
    public static final boolean HEADLESS = false;
    public static final String BROWSER = "chrome";
}
Page Object Model Implementation
Each page class extends BasePage and implements:

Locator definitions using By selectors

Business logic methods

Element interaction with built-in waiting

Page-specific assertions

Synchronization Strategy
java
// Example of robust element interaction
protected void clickElement(By locator) {
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    element.click();
}
🧪 Test Execution Details
Test Flow
Initialization - WebDriver setup and browser launch

Account Creation - Complete user registration with dynamic email generation

Product Search - Search functionality with Enter key implementation

Product Selection - First product selection with image verification

Cart Operations - Add to cart and navigation

Validation - Comprehensive cart verification

Cleanup - Proper resource disposal

Test Reports
Console output with detailed step-by-step logging

TestNG HTML reports in target/surefire-reports/

Screenshot capture on test failures (if implemented)

🔧 Configuration Options
Modify src/test/java/com/prestashop/config/Config.java for different environments:

java
public class Config {
    public static final String BASE_URL = "https://demo.prestashop.com/";
    public static final int TIMEOUT = 15; // Increase for slower environments
    public static final boolean HEADLESS = true; // For CI/CD pipelines
    public static final String BROWSER = "firefox"; // chrome, firefox, edge
}
🚀 Performance Optimizations
Smart Waiting: Conditional waits instead of fixed sleeps

Efficient Locators: Optimized CSS selectors for better performance

Resource Management: Proper WebDriver lifecycle management

Parallel Ready: Thread-safe implementation for future scaling

🐛 Troubleshooting
Common Issues & Solutions
Element Not Found Errors:

Verify PrestaShop demo site is accessible

Check for iframe contexts in the application

Adjust timeout values in Config.java

Browser Compatibility:

Ensure Chrome/Firefox is updated to latest version

Verify WebDriverManager can download appropriate drivers

Check firewall settings for driver downloads

Test Failures:

Review console logs for specific error messages

Verify network connectivity to demo site

Check for application loading states

Debug Mode
Enable additional logging by modifying wait timeouts and adding debug statements in page classes.

📈 Extending the Framework
Adding New Tests
Create new test class in tests package

Follow existing patterns for setup/teardown

Use page objects for all UI interactions

Implement comprehensive assertions

Adding New Pages
Extend BasePage.java

Define locators using By selectors

Implement business logic methods

Add page-specific verification methods

🤝 Best Practices Demonstrated
✅ Page Object Model design pattern

✅ DRY Principle - No code duplication

✅ Robust Synchronization - No hardcoded waits

✅ Clean Code - Meaningful method names and structure

✅ Error Handling - Graceful failure recovery

✅ Configuration Management - Externalized settings

✅ Maintainable Selectors - CSS-based locators

✅ Comprehensive Logging - Step-by-step execution tracking

📞 Support
For issues related to:

Test Execution: Check browser compatibility and network connectivity

Framework Questions: Review the page object implementation patterns

Environment Setup: Verify Java, Maven, and browser installations

📄 License
This project is developed as part of technical assessment and demonstrates professional test automation practices.

Built with professional test automa
