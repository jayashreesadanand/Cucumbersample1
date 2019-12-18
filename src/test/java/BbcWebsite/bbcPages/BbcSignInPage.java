package BbcWebsite.bbcPages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BbcSignInPage {
    // Set up driver
    private WebDriver driver;
    // Set page path
    private String signInPageURL = "https://account.bbc.com/signin";
    // Page object identifier
    private By usernameFieldID = By.id("user-identifier-input");
    private By passwordFieldID = By.id("password-input");
    private By submitButtonID = By.id("submit-button");
    private By incorrctemailID = By.id("form-message-username");
    private By incorrectPasswordErrorID = By.id("form-message-password");
    private String errorText = "Sorry, that password isn't valid. Please include a letter.";

    public BbcSignInPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToSignInPage(){
        driver.navigate().to(signInPageURL);
    }

    public void inputUserName(String name){
        driver.findElement(usernameFieldID).sendKeys(name);
    }

    public void inputPassword(String password){
        driver.findElement(passwordFieldID).sendKeys(password);
    }

    public void clicksubmitButton(){
        driver.findElement(submitButtonID).click();
    }

    public String getPasswordErrorText(){
        return driver.findElement(incorrectPasswordErrorID).getText();
    }

    public String emailErrorText(){
        return driver.findElement(incorrctemailID).getText();
    }

    public String getSignInPageURL() { return signInPageURL;  }

    public String getErrorText() {
        return errorText;
    }
    // Page assertion

    public void submitButtonIsVisible(){
       Assert.assertTrue(driver.findElement(submitButtonID).isDisplayed());
    }



}
