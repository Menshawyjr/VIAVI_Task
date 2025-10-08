package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends BasePage {

    private By searchResults = By.cssSelector(".products article");
    private By firstProduct = By.cssSelector(".products article:first-child a");
    private By productNames = By.cssSelector(".product-title a");
    private By searchHeader = By.cssSelector(".page-header");

    public SearchPage(WebDriver driver) {
        super(driver);
        waitForFullLoad();
        verifySearchResults();
    }

    private void verifySearchResults() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(searchResults));
            List<WebElement> results = driver.findElements(searchResults);
            System.out.println("Found " + results.size() + " search results");
        } catch (Exception e) {
            System.out.println("No search results found or page not loaded properly");
        }
    }

    public ProductPage selectFirstProduct() {
        System.out.println("Selecting first search result...");

        try {
            WebElement firstProductLink = driver.findElement(firstProduct);
            // Scroll to the product to ensure it's visible
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", firstProductLink);
            Thread.sleep(1000);

            firstProductLink.click();
            System.out.println("Clicked on first product");
        } catch (Exception e) {
            System.out.println("Could not click first product: " + e.getMessage());
            // Try alternative approach
            List<WebElement> products = driver.findElements(productNames);
            if (!products.isEmpty()) {
                products.get(0).click();
                System.out.println("Clicked first product using alternative locator");
            } else {
                throw new RuntimeException("No products found in search results");
            }
        }

        waitForFullLoad();
        return new ProductPage(driver);
    }

    public String getFirstProductName() {
        try {
            List<WebElement> products = driver.findElements(productNames);
            if (!products.isEmpty()) {
                return products.get(0).getText();
            }
        } catch (Exception e) {
            System.out.println("Could not get first product name: " + e.getMessage());
        }
        return "";
    }

    public int getSearchResultsCount() {
        List<WebElement> results = driver.findElements(searchResults);
        return results.size();
    }
}