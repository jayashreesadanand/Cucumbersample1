package GovUkWebsite.govukPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GovUkHomePage {

    private WebDriver driver;

    private String homePageURL = System.getProperty("govuk.webapp.url", "http://localhost:3000") + "/";
    private By registerButtonSelector = By.linkText("Register");
    private By accessFormsButtonSelector = By.linkText("Access Forms");

    public GovUkHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToHomePage() {
        driver.navigate().to(homePageURL);
    }

    public void clickRegisterButton() {
        driver.findElement(registerButtonSelector).click();
    }

    public void clickAccessFormsButton() {
        driver.findElement(accessFormsButtonSelector).click();
    }
}
