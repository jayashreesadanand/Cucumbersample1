package GovUkWebsite.govukPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GovUkFormsPage {

    private WebDriver driver;

    private String formsPageURL = System.getProperty("govuk.webapp.url", "http://localhost:3000") + "/forms";
    private By headingSelector = By.cssSelector("h1.govuk-heading-l");
    private By govukFormsLinkSelector = By.cssSelector("a.govuk-button--start");

    public GovUkFormsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToFormsPage() {
        driver.navigate().to(formsPageURL);
    }

    public String getPageHeadingText() {
        return driver.findElement(headingSelector).getText();
    }

    public boolean isGovUkFormsLinkDisplayed() {
        return driver.findElement(govukFormsLinkSelector).isDisplayed();
    }
}
