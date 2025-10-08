package tests;

import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;
import pages.ProductPage;
import org.testng.Assert;
import java.time.Duration;
import org.openqa.selenium.By;
import utils.WebDriverFactory;
import pages.AuthenticationPage;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PrestaShopTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        System.out.println("Opening PrestaShop demo site...");
        driver.get("https://demo.prestashop.com/");
        // Use multiple wait strategies
        waitForShopToLoadCompletely();
    }

    private void waitForShopToLoadCompletely() {
        System.out.println("Waiting for shop to be fully available...");
        try {
            // Wait for the iframe to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            System.out.println("Iframe detected");

            // Switch to the iframe if it exists
            WebDriverWait frameWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            frameWait.until(driver -> {
                try {
                    java.util.List<org.openqa.selenium.WebElement> iframes = driver.findElements(By.tagName("iframe"));
                    if (!iframes.isEmpty()) {
                        driver.switchTo().frame(iframes.get(0));
                        System.out.println("Switched to iframe");
                        return true;
                    }
                    return true; // No iframe, continue
                } catch (Exception e) {
                    return false;
                }
            });

            // Wait for loading message to disappear
            By loadingLocator = By.xpath("//*[contains(text(), 'A shop is on its way') or contains(text(), 'STRATEGY') or contains(text(), 'PRESTASHOP')]");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingLocator));
            System.out.println("Loading message disappeared");

            // Wait for page to be interactive
            wait.until(driver -> {
                String readyState = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
                return "complete".equals(readyState);
            });
            System.out.println("Page is interactive");

        } catch (Exception e) {
            System.out.println("Shop loading completed with exception: " + e.getMessage());
            // Continue anyway
        }
    }

    @Test
    public void testCompleteScenario() throws InterruptedException {
        System.out.println("=== Starting Complete Test Scenario ===");
        HomePage homePage = new HomePage(driver);

        // Step 2: Create an account
        System.out.println("--- Step 2: Creating Account ---");
        LoginPage loginPage = homePage.goToLoginPage();
        AuthenticationPage authPage = loginPage.goToRegistration();

        // Fill registration form with strong password
        System.out.println("Filling registration form with strong password...");
        authPage.fillRegistrationForm("John", "Doe");

        homePage = authPage.submitRegistration();

        // Verify user is logged in
        boolean isLoggedIn = homePage.isUserLoggedIn();
        Assert.assertTrue(isLoggedIn, "User should be logged in after registration");
        System.out.println("✓ Account created successfully - User is logged in");

        // Step 3: From the homepage, search for "notebook"
        System.out.println("--- Step 3: Searching for 'notebook' ---");
        SearchPage searchPage = homePage.searchProduct("notebook");

        int resultsCount = searchPage.getSearchResultsCount();
        Assert.assertTrue(resultsCount > 0, "Should find at least one notebook product");
        System.out.println("✓ Found " + resultsCount + " search results for 'notebook'");

        String firstProductName = searchPage.getFirstProductName();
        System.out.println("First product name: " + firstProductName);

        // Step 4: Select the first search result and assert that it has an image
        System.out.println("--- Step 4: Selecting First Product and Verifying Image ---");
        ProductPage productPage = searchPage.selectFirstProduct();
        String productName = productPage.getProductName();
        System.out.println("Product page opened: " + productName);
        boolean hasImage = productPage.hasProductImage();
        Assert.assertTrue(hasImage, "Product should have an image");
        System.out.println("✓ Product image verification passed");

        // Step 5: Add it to the cart
        System.out.println("--- Step 5: Adding Product to Cart ---");
        productPage.addToCart();
        System.out.println("✓ Product added to cart successfully");

        // Step 6: Navigate to the cart
        System.out.println("--- Step 6: Navigating to Cart ---");
        CartPage cartPage = productPage.proceedToCart();
        System.out.println("✓ Navigated to cart page");

        // Step 7: Assert that the product is successfully added
        System.out.println("--- Step 7: Verifying Product in Cart ---");
        boolean isInCart = cartPage.isProductInCart();
        Assert.assertTrue(isInCart, "Product should be in cart");
        int itemCount = cartPage.getCartItemCount();
        Assert.assertTrue(itemCount > 0, "Cart should have items");
        String cartProductName = cartPage.getFirstProductName();
        System.out.println("Product in cart: " + cartProductName);
        System.out.println("✓ Cart verification passed. Items in cart: " + itemCount);
        System.out.println("=== Complete Test Scenario Finished Successfully! ===");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}