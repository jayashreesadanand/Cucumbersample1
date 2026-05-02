package GovUkWebsite.govukPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GovUkRegistrationConfirmationPage {

    private WebDriver driver;

    private String confirmationPageURL = System.getProperty("govuk.webapp.url", "http://localhost:3000") + "/register/confirmation";
    private By panelTitleSelector = By.cssSelector(".govuk-panel__title");
    private By panelBodySelector = By.cssSelector(".govuk-panel__body");
    private By accessFormsButtonSelector = By.linkText("Access Forms");

    public GovUkRegistrationConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToConfirmationPage() {
        driver.navigate().to(confirmationPageURL);
    }

    public boolean isPanelTitleDisplayed() {
        return driver.findElement(panelTitleSelector).isDisplayed();
    }

    public String getPanelBodyText() {
        return driver.findElement(panelBodySelector).getText();
    }

    public void clickAccessFormsButton() {
        driver.findElement(accessFormsButtonSelector).click();
    }
}
