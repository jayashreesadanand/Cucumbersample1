package GovUkWebsite;

import GovUkWebsite.govukPages.GovUkFormsPage;
import GovUkWebsite.govukPages.GovUkHomePage;
import GovUkWebsite.govukPages.GovUkRegistrationConfirmationPage;
import GovUkWebsite.govukPages.GovUkRegistrationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class GovUkSite {

    private WebDriver driver;
    private GovUkHomePage homePage;
    private GovUkRegistrationPage registrationPage;
    private GovUkRegistrationConfirmationPage registrationConfirmationPage;
    private GovUkFormsPage formsPage;

    public GovUkSite() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        this.driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage = new GovUkHomePage(driver);
        registrationPage = new GovUkRegistrationPage(driver);
        registrationConfirmationPage = new GovUkRegistrationConfirmationPage(driver);
        formsPage = new GovUkFormsPage(driver);
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void quit() {
        driver.quit();
    }

    public GovUkHomePage homePage() {
        return homePage;
    }

    public GovUkRegistrationPage registrationPage() {
        return registrationPage;
    }

    public GovUkRegistrationConfirmationPage registrationConfirmationPage() {
        return registrationConfirmationPage;
    }

    public GovUkFormsPage formsPage() {
        return formsPage;
    }
}
