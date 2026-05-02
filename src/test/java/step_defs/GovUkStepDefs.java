package step_defs;

import GovUkWebsite.GovUkSite;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.NotificationList;
import uk.gov.service.notify.Notification;

import java.util.List;

public class GovUkStepDefs {

    GovUkSite govUkSite = new GovUkSite();
    private NotificationClient notifyClient;
    private int emailCountBeforeTest;

    // ── Navigation ──────────────────────────────────────────────────────────

    @Given("I navigate to the GOV.UK service home page")
    public void i_navigate_to_the_govuk_service_home_page() {
        govUkSite.homePage().goToHomePage();
    }

    @When("I click the {string} button")
    public void i_click_the_button(String buttonText) {
        switch (buttonText) {
            case "Register":
                govUkSite.homePage().clickRegisterButton();
                break;
            case "Access Forms":
                govUkSite.homePage().clickAccessFormsButton();
                break;
            default:
                throw new IllegalArgumentException("Unknown button: " + buttonText);
        }
    }

    // ── Registration form ────────────────────────────────────────────────────

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        govUkSite.registrationPage().goToRegistrationPage();
    }

    @Then("I should see the registration form with a {string} field")
    public void i_should_see_the_registration_form_with_a_field(String fieldLabel) {
        if ("Full name".equals(fieldLabel)) {
            govUkSite.registrationPage().assertFullNameFieldVisible();
        }
    }

    @When("I enter full name {string}")
    public void i_enter_full_name(String fullName) {
        govUkSite.registrationPage().enterFullName(fullName);
    }

    @When("I enter email address {string}")
    public void i_enter_email_address(String email) {
        govUkSite.registrationPage().enterEmail(email);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        govUkSite.registrationPage().enterPassword(password);
    }

    @When("I submit the registration form")
    public void i_submit_the_registration_form() {
        govUkSite.registrationPage().submitForm();
    }

    @Then("I should see an error summary")
    public void i_should_see_an_error_summary() {
        Assert.assertTrue("Error summary should be displayed",
                govUkSite.registrationPage().isErrorSummaryDisplayed());
    }

    @And("I should see the error {string}")
    public void i_should_see_the_error(String errorMessage) {
        Assert.assertTrue("Error message should be present: " + errorMessage,
                govUkSite.registrationPage().isErrorMessagePresent(errorMessage));
    }

    // ── Registration confirmation ────────────────────────────────────────────

    @Then("I should see the registration confirmation page")
    public void i_should_see_the_registration_confirmation_page() {
        Assert.assertTrue("Confirmation panel should be displayed",
                govUkSite.registrationConfirmationPage().isPanelTitleDisplayed());
    }

    @And("the confirmation page should show {string}")
    public void the_confirmation_page_should_show(String name) {
        Assert.assertTrue("Confirmation panel body should contain the user's name",
                govUkSite.registrationConfirmationPage().getPanelBodyText().contains(name));
    }

    @Given("I am on the registration confirmation page for {string}")
    public void i_am_on_the_registration_confirmation_page_for(String name) {
        govUkSite.registrationConfirmationPage().goToConfirmationPage();
    }

    // ── Forms page ───────────────────────────────────────────────────────────

    @Then("I should see the forms page heading {string}")
    public void i_should_see_the_forms_page_heading(String expectedHeading) {
        Assert.assertEquals(expectedHeading, govUkSite.formsPage().getPageHeadingText());
    }

    @And("I should see a link to GOV.UK Forms")
    public void i_should_see_a_link_to_govuk_forms() {
        Assert.assertTrue("GOV.UK Forms link should be displayed",
                govUkSite.formsPage().isGovUkFormsLinkDisplayed());
    }

    // ── GOV.UK Notify assertions ─────────────────────────────────────────────

    @Given("the GOV.UK Notify service is configured")
    public void the_govuk_notify_service_is_configured() {
        String notifyApiKey = System.getProperty("notify.api.key", System.getenv("NOTIFY_API_KEY"));
        if (notifyApiKey != null && !notifyApiKey.isEmpty()) {
            notifyClient = new NotificationClient(notifyApiKey);
        }
        emailCountBeforeTest = getEmailCount();
    }

    @Then("a confirmation email should be sent to {string}")
    public void a_confirmation_email_should_be_sent_to(String email) {
        if (notifyClient == null) {
            System.out.println("NOTIFY_API_KEY not configured – skipping live email assertion for: " + email);
            return;
        }
        int emailCountAfterTest = getEmailCount();
        Assert.assertTrue("Expected a new confirmation email to be sent via GOV.UK Notify",
                emailCountAfterTest > emailCountBeforeTest);
    }

    @And("the email should contain the user's name {string}")
    public void the_email_should_contain_the_users_name(String name) {
        if (notifyClient == null) {
            System.out.println("NOTIFY_API_KEY not configured – skipping email content assertion for name: " + name);
            return;
        }
        try {
            NotificationList notifications = notifyClient.getNotifications("email", "delivered", null, null);
            List<Notification> sent = notifications.getNotifications();
            boolean found = sent.stream().anyMatch(n -> {
                Object namePart = n.getPersonalisation().get("name");
                return namePart != null && namePart.toString().equals(name);
            });
            Assert.assertTrue("Expected confirmation email to contain name: " + name, found);
        } catch (NotificationClientException e) {
            Assert.fail("GOV.UK Notify API error: " + e.getMessage());
        }
    }

    @Then("no confirmation email should be sent")
    public void no_confirmation_email_should_be_sent() {
        if (notifyClient == null) {
            System.out.println("NOTIFY_API_KEY not configured – skipping no-email assertion");
            return;
        }
        int emailCountAfterTest = getEmailCount();
        Assert.assertEquals("Expected no new confirmation emails to be sent",
                emailCountBeforeTest, emailCountAfterTest);
    }

    private int getEmailCount() {
        if (notifyClient == null) {
            return 0;
        }
        try {
            NotificationList notifications = notifyClient.getNotifications("email", null, null, null);
            return notifications.getNotifications().size();
        } catch (NotificationClientException e) {
            System.err.println("Could not query GOV.UK Notify: " + e.getMessage());
            return 0;
        }
    }

    // ── Teardown ─────────────────────────────────────────────────────────────

    @After
    public void tearDown() {
        govUkSite.quit();
    }
}
