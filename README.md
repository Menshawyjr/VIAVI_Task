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

Page Object Model Implementation
Each page class extends BasePage and implements:

Locator definitions using By selectors

Business logic methods

Element interaction with built-in waiting

Page-specific assertions

🧪 Test Execution Details
Test Flow
Initialization - WebDriver setup and browser launch
Account Creation - Complete user registration with dynamic email generation
Product Search - Search functionality with Enter key implementation
Product Selection - First product selection with image verification
Cart Operations - Add to cart and navigation
Validation - Comprehensive cart verification
Cleanup - Proper resource disposal
