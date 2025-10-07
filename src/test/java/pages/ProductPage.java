package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    private By productImage = By.cssSelector(".product-cover img, .product-images img");
    private By addToCartButton = By.cssSelector(".add-to-cart, .btn-primary[data-button-action='add-to-cart']");
    private By productName = By.cssSelector(".h1, h1[itemprop='name']");
    private By cartModal = By.cssSelector(".cart-modal, #blockcart-modal, .modal-dialog");
    private By proceedToCheckout = By.cssSelector("a[href*='controller=cart'], .btn-primary[href*='cart']");
    private By continueShopping = By.cssSelector(".btn-secondary, .btn[data-dismiss='modal']");

    public ProductPage(WebDriver driver) {
        super(driver);
        waitForFullLoad();
    }

    public boolean hasProductImage() {
        System.out.println("Checking if product has an image...");
        try {
            WebElement image = wait.until(ExpectedConditions.presenceOfElementLocated(productImage));
            boolean isDisplayed = image.isDisplayed();
            boolean hasSrc = image.getAttribute("src") != null && !image.getAttribute("src").isEmpty();

            System.out.println("Product image - Displayed: " + isDisplayed + ", Has source: " + hasSrc);
            return isDisplayed && hasSrc;
        } catch (Exception e) {
            System.out.println("Product image not found: " + e.getMessage());
            return false;
        }
    }

    public void addToCart() throws InterruptedException {
        System.out.println("Adding product to cart...");

        try {
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

            // Scroll to the button
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", addButton);
            Thread.sleep(1000);

            addButton.click();
            System.out.println("Clicked Add to Cart button");

            // Wait for cart modal to appear
            waitForCartModal();

        } catch (Exception e) {
            System.out.println("Could not add product to cart: " + e.getMessage());
            throw e;
        }
    }

    private void waitForCartModal() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartModal));
            System.out.println("Cart modal appeared");

            // Wait a bit more for animation
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Cart modal might not have appeared: " + e.getMessage());
        }
    }

    public CartPage proceedToCart() {
        System.out.println("Proceeding to cart...");

        try {
            // Try to find and click "Proceed to checkout" in the modal
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckout));
            checkoutBtn.click();
            System.out.println("Clicked Proceed to Cart/Checkout");
        } catch (Exception e) {
            System.out.println("Could not find proceed button in modal: " + e.getMessage());

            // Alternative: If modal has continue shopping, close it and navigate to cart via header
            try {
                WebElement continueBtn = driver.findElement(continueShopping);
                continueBtn.click();
                System.out.println("Clicked Continue Shopping");

                // Navigate to cart via header cart icon
                By cartIcon = By.cssSelector(".shopping-cart, .cart-preview, a[href*='cart']");
                WebElement cart = driver.findElement(cartIcon);
                cart.click();
                System.out.println("Navigated to cart via header icon");
            } catch (Exception ex) {
                System.out.println("Could not navigate to cart: " + ex.getMessage());
                // Direct navigation as last resort
                driver.get(driver.getCurrentUrl().replace("product", "cart"));
                System.out.println("Direct navigation to cart");
            }
        }

        waitForFullLoad();
        return new CartPage(driver);
    }

    public String getProductName() {
        try {
            WebElement nameElement = driver.findElement(productName);
            return nameElement.getText();
        } catch (Exception e) {
            return "Unknown Product";
        }
    }
}