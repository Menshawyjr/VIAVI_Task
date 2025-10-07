package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

public class HomePage extends BasePage {

    private By signInLink = By.cssSelector("a[title='Log in to your customer account']");
    private By searchInput = By.cssSelector("input[name='s']");
    private By userAccount = By.cssSelector(".account");

    public HomePage(WebDriver driver) {
        super(driver);
        waitForFullLoad();
    }

    public LoginPage goToLoginPage() {
        WebElement signIn = driver.findElement(signInLink);
        signIn.click();
        waitForFullLoad();
        return new LoginPage(driver);
    }

    public SearchPage searchProduct(String productName) {
        System.out.println("Searching for: " + productName);

        WebElement searchBox = driver.findElement(searchInput);
        searchBox.clear();
        searchBox.sendKeys(productName);
        searchBox.sendKeys(Keys.ENTER);

        waitForFullLoad();
        return new SearchPage(driver);
    }

    public boolean isUserLoggedIn() {
        try {
            WebElement accountElement = driver.findElement(userAccount);
            return accountElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}