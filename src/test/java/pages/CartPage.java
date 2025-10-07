package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage extends BasePage {

    private By cartItems = By.cssSelector(".cart-item, .cart-detailed, tr.cart-item");
    private By productNames = By.cssSelector(".product-name, .cart-item-name");
    private By cartQuantity = By.cssSelector(".cart-quantity, .js-cart-line-product-quantity");
    private By cartSubtotal = By.cssSelector(".cart-subtotal, .subtotal");
    private By emptyCartMessage = By.cssSelector(".no-items, .cart-empty");
    private By proceedToCheckoutBtn = By.cssSelector(".checkout, .btn-primary[href*='order']");

    public CartPage(WebDriver driver) {
        super(driver);
        waitForFullLoad();
        verifyCartPageLoaded();
    }

    private void verifyCartPageLoaded() {
        try {
            // Wait for either cart items or empty cart message
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(cartItems),
                    ExpectedConditions.presenceOfElementLocated(emptyCartMessage)
            ));
            System.out.println("Cart page loaded successfully");
        } catch (Exception e) {
            System.out.println("Cart page might not have loaded properly: " + e.getMessage());
        }
    }

    public boolean isProductInCart() {
        System.out.println("Checking if product is in cart...");

        List<WebElement> items = driver.findElements(cartItems);
        boolean hasItems = !items.isEmpty();

        System.out.println("Cart has items: " + hasItems + " (Count: " + items.size() + ")");

        if (hasItems) {
            // Log product names for verification
            List<WebElement> productNameElements = driver.findElements(productNames);
            for (WebElement product : productNameElements) {
                System.out.println("Product in cart: " + product.getText());
            }
        }

        return hasItems;
    }

    public int getCartItemCount() {
        List<WebElement> items = driver.findElements(cartItems);
        return items.size();
    }

    public String getFirstProductName() {
        try {
            List<WebElement> productNameElements = driver.findElements(productNames);
            if (!productNameElements.isEmpty()) {
                return productNameElements.get(0).getText();
            }
        } catch (Exception e) {
            System.out.println("Could not get product name from cart: " + e.getMessage());
        }
        return "";
    }

    public boolean isCartEmpty() {
        try {
            WebElement emptyMessage = driver.findElement(emptyCartMessage);
            return emptyMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public double getCartSubtotal() {
        try {
            WebElement subtotalElement = driver.findElement(cartSubtotal);
            String subtotalText = subtotalElement.getText();
            // Extract numeric value from text like "$29.00" or "€29,00"
            String numericValue = subtotalText.replaceAll("[^0-9.,]", "").replace(",", ".");
            return Double.parseDouble(numericValue);
        } catch (Exception e) {
            System.out.println("Could not get cart subtotal: " + e.getMessage());
            return 0.0;
        }
    }
}