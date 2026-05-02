package GovUkWebsite.govukPages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GovUkRegistrationPage {

    private WebDriver driver;

    private String registrationPageURL = System.getProperty("govuk.webapp.url", "http://localhost:3000") + "/register";

    private By fullNameFieldID = By.id("fullName");
    private By emailFieldID = By.id("email");
    private By passwordFieldID = By.id("password");
    private By submitButtonSelector = By.cssSelector("button[type='submit']");
    private By errorSummarySelector = By.cssSelector(".govuk-error-summary");

    public GovUkRegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToRegistrationPage() {
        driver.navigate().to(registrationPageURL);
    }

    public void enterFullName(String fullName) {
        driver.findElement(fullNameFieldID).clear();
        driver.findElement(fullNameFieldID).sendKeys(fullName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailFieldID).clear();
        driver.findElement(emailFieldID).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordFieldID).clear();
        driver.findElement(passwordFieldID).sendKeys(password);
    }

    public void submitForm() {
        driver.findElement(submitButtonSelector).click();
    }

    public boolean isErrorSummaryDisplayed() {
        return driver.findElement(errorSummarySelector).isDisplayed();
    }

    public boolean isErrorMessagePresent(String errorMessage) {
        return driver.getPageSource().contains(errorMessage);
    }

    public void assertFullNameFieldVisible() {
        Assert.assertTrue("Full name field should be visible",
                driver.findElement(fullNameFieldID).isDisplayed());
    }
}
