package pages;

import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class AuthenticationPage extends BasePage {
    private By socialTitleMr = By.id("field-id_gender-1");
    private By socialTitleMrs = By.id("field-id_gender-2");
    private By firstNameInput = By.name("firstname");
    private By lastNameInput = By.name("lastname");
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By birthdateInput = By.name("birthday");
    private By offersCheckbox = By.name("optin");
    private By termsCheckbox = By.name("psgdpr");
    private By newsletterCheckbox = By.name("newsletter");
    private By customerPrivacyCheckbox = By.name("customer_privacy");
    private By saveButton = By.xpath("//button[contains(text(), 'Save') or contains(@class, 'btn-primary')]");
    private By passwordStrength = By.cssSelector(".password-strength"); // To check password strength

    public AuthenticationPage(WebDriver driver) {
        super(driver);
        waitForFullLoad();
    }

    public void fillRegistrationForm(String firstName, String lastName) {
        System.out.println("Filling registration form...");

        // Select gender (Mr.)
        try {
            WebElement genderMr = driver.findElement(socialTitleMr);
            if (!genderMr.isSelected()) {
                genderMr.click();
            }
            System.out.println("Selected Mr. as social title");
        } catch (Exception e) {
            System.out.println("Could not select social title: " + e.getMessage());
        }

        // Fill first name
        WebElement firstNameField = driver.findElement(firstNameInput);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        System.out.println("Filled first name: " + firstName);

        // Fill last name
        WebElement lastNameField = driver.findElement(lastNameInput);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        System.out.println("Filled last name: " + lastName);

        // Fill email (generate unique if needed)
        WebElement emailField = driver.findElement(emailInput);
        String currentEmail = emailField.getAttribute("value");
        if (currentEmail == null || currentEmail.isEmpty()) {
            String uniqueEmail = generateUniqueEmail();
            emailField.clear();
            emailField.sendKeys(uniqueEmail);
            System.out.println("Filled email: " + uniqueEmail);
        } else {
            System.out.println("Email already filled: " + currentEmail);
        }

        // Fill STRONG password
        String strongPassword = generateStrongPassword();
        WebElement passwordField = driver.findElement(passwordInput);
        passwordField.clear();
        passwordField.sendKeys(strongPassword);
        System.out.println("Filled strong password: " + strongPassword);

        // Wait for password strength validation
        waitForPasswordStrength();

        // Fill birthdate in correct format (YYYY-MM-DD)
        try {
            WebElement birthdateField = driver.findElement(birthdateInput);
            birthdateField.clear();
            birthdateField.sendKeys("1990-05-31"); // Use YYYY-MM-DD format
            System.out.println("Filled birthdate: 1990-05-31");
        } catch (Exception e) {
            System.out.println("Birthdate field not available or optional");
        }

        // Check required checkboxes
        checkRequiredCheckboxes();
    }

    // Generate a strong password that meets requirements
    private String generateStrongPassword() {
        // Strong password: at least 8 characters with uppercase, lowercase, numbers, and special characters
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String special = "!@#$%^&*";

        StringBuilder password = new StringBuilder();

        // Ensure at least one of each type
        password.append(uppercase.charAt((int) (Math.random() * uppercase.length())));
        password.append(lowercase.charAt((int) (Math.random() * lowercase.length())));
        password.append(numbers.charAt((int) (Math.random() * numbers.length())));
        password.append(special.charAt((int) (Math.random() * special.length())));

        // Fill remaining characters to make it 12 characters long
        String allChars = uppercase + lowercase + numbers + special;
        for (int i = 4; i < 12; i++) {
            password.append(allChars.charAt((int) (Math.random() * allChars.length())));
        }

        // Shuffle the password
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = (int) (Math.random() * passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        return new String(passwordArray);
    }

    // Alternative simple strong passwords (choose one)
    private String getPredefinedStrongPassword() {
        String[] strongPasswords = {
                "StrongPass123!",
                "SecurePwd456@",
                "TestPass789#",
                "Password123$",
                "AdminPass!123",
                "UserPass@456",
                "DemoPass#789",
                "TempPass$012"
        };
        return strongPasswords[(int) (Math.random() * strongPasswords.length)];
    }

    // Wait for password strength indicator to show "Strong"
    private void waitForPasswordStrength() {
        System.out.println("Waiting for password strength validation...");
        try {
            // Wait a moment for the strength indicator to update
            Thread.sleep(2000);

            // Check if there's a password strength indicator
            try {
                WebElement strengthIndicator = driver.findElement(passwordStrength);
                String strengthText = strengthIndicator.getText();
                System.out.println("Password strength: " + strengthText);

                // If not strong, try a different password
                if (strengthText != null && !strengthText.toLowerCase().contains("strong")) {
                    System.out.println("Password not strong enough, trying alternative...");
                    WebElement passwordField = driver.findElement(passwordInput);
                    passwordField.clear();
                    String alternativePassword = getPredefinedStrongPassword();
                    passwordField.sendKeys(alternativePassword);
                    System.out.println("Used alternative password: " + alternativePassword);
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                System.out.println("No password strength indicator found");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted while waiting for password strength");
        }
    }

    private void checkRequiredCheckboxes() {
        System.out.println("Checking required checkboxes...");

        // Customer data privacy (usually required)
        try {
            WebElement customerPrivacy = driver.findElement(customerPrivacyCheckbox);
            if (!customerPrivacy.isSelected()) {
                customerPrivacy.click();
                System.out.println("Checked Customer data privacy");
            }
        } catch (Exception e) {
            System.out.println("Customer privacy checkbox not found");
        }

        // Terms and conditions
        try {
            WebElement terms = driver.findElement(termsCheckbox);
            if (!terms.isSelected()) {
                terms.click();
                System.out.println("Checked Terms and conditions");
            }
        } catch (Exception e) {
            System.out.println("Terms checkbox not found");
        }

        // Optional checkboxes
        try {
            WebElement newsletter = driver.findElement(newsletterCheckbox);
            if (!newsletter.isSelected()) {
                newsletter.click();
                System.out.println("Checked Newsletter (optional)");
            }
        } catch (Exception e) {
            System.out.println("Newsletter checkbox not found");
        }

        try {
            WebElement offers = driver.findElement(offersCheckbox);
            if (!offers.isSelected()) {
                offers.click();
                System.out.println("Checked Offers from partners (optional)");
            }
        } catch (Exception e) {
            System.out.println("Offers checkbox not found");
        }
    }

    public HomePage submitRegistration() {
        System.out.println("Submitting registration form...");

        // Scroll to save button to ensure it's visible
        try {
            WebElement saveBtn = driver.findElement(saveButton);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", saveBtn);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Could not scroll to save button");
        }

        // Click save button
        try {
            WebElement saveBtn = driver.findElement(saveButton);
            saveBtn.click();
            System.out.println("Clicked Save button");
        } catch (Exception e) {
            System.out.println("Could not click Save button: " + e.getMessage());
            // Try alternative locator
            try {
                By altSaveButton = By.cssSelector("button[type='submit'], .btn-primary");
                WebElement altSaveBtn = driver.findElement(altSaveButton);
                altSaveBtn.click();
                System.out.println("Clicked Save button using alternative locator");
            } catch (Exception ex) {
                System.out.println("Could not click Save button with alternative locator either");
                throw ex;
            }
        }

        // Wait for registration to complete
        waitForFullLoad();

        System.out.println("Registration submission completed");
        return new HomePage(driver);
    }

    // Helper method to generate unique email
    public String generateUniqueEmail() {
        return "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
    }
}