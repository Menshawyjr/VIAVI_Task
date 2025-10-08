package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    // More robust method to wait for shop to load
    protected void waitForShopToLoad() {
        System.out.println("Waiting for shop to load...");

        try {
            // Strategy 1: Wait for loading message to disappear
            By loadingLocator = By.xpath("//*[contains(text(), 'A shop is on its way') or contains(text(), 'STRATEGY')]");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingLocator));
            System.out.println("Loading message disappeared");

            // Strategy 2: Wait for main content to be visible
            By mainContentLocator = By.cssSelector("main, #main, .page-content, .products");
            wait.until(ExpectedConditions.presenceOfElementLocated(mainContentLocator));
            System.out.println("Main content loaded");

            // Strategy 3: Wait for key elements to be clickable
            By keyElements = By.cssSelector("a, button, input, .product");
            wait.until(ExpectedConditions.elementToBeClickable(keyElements));
            System.out.println("Key elements are clickable");

        } catch (Exception e) {
            System.out.println("Shop loading wait completed with exception: " + e.getMessage());
        }

        System.out.println("Shop loading wait finished");
    }

    // Alternative method using JavaScript ready state
    protected void waitForPageToLoadCompletely() {
        System.out.println("Waiting for page to load completely...");

        try {
            // Wait for JavaScript to complete loading
            wait.until(driver -> {
                String readyState = ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return document.readyState;").toString();
                return readyState.equals("complete");
            });
            System.out.println("Document ready state is complete");

            // Additional wait for dynamic content
            Thread.sleep(5000);
            System.out.println("Page load wait completed");

        } catch (Exception e) {
            System.out.println("Page load wait completed with exception: " + e.getMessage());
        }
    }

    // Combined method that uses both strategies
    protected void waitForFullLoad() {
        waitForShopToLoad();
        waitForPageToLoadCompletely();
    }

    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    protected void sendKeys(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }
}