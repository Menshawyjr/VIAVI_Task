package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private By createAccountLink = By.xpath("//a[contains(text(), 'Create one here')]");
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By signInButton = By.id("submit-login");

    public LoginPage(WebDriver driver) {
        super(driver);
        waitForShopToLoad();
    }

    public AuthenticationPage goToRegistration() {
        WebElement createAccount = driver.findElement(createAccountLink);
        createAccount.click();
        waitForShopToLoad();
        return new AuthenticationPage(driver);
    }

    // Optional: Method for login if needed later
    public HomePage login(String email, String password) {
        WebElement emailField = driver.findElement(emailInput);
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(passwordInput);
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement signInBtn = driver.findElement(signInButton);
        signInBtn.click();
        waitForShopToLoad();

        return new HomePage(driver);
    }
}