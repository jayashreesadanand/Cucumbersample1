package step_defs;

import BbcWebsite.BbcSite;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class BbcSeleniumStepDefs {

    BbcSite bbcSite = new BbcSite();

    @Given("I can access the bbc home page")
    public void i_can_access_the_bbc_home_page() throws Throwable {
        bbcSite.bbcHomePage().gotoHomepage();
    }

    @When("I click the sign in link")
    public void i_click_the_sign_in_link() throws Throwable {
        bbcSite.bbcHomePage().clickSignInLink();
    }

    @Then("I should access the sign in page")
    public void i_should_access_the_sign_in_page() throws Throwable {
        bbcSite.bbcSignInPage().submitButtonIsVisible();
    }


    @Given("I am on the Sign-In page")
    public void i_am_on_the_Sign_In_page() throws Throwable {
        bbcSite.bbcSignInPage().goToSignInPage();
    }

    @When("I input username {string}")
    public void i_input_username(String username) {
        // Write code here that turns the phrase above into concrete actions
        bbcSite.bbcSignInPage().inputUserName(username);
    }


    @When("I input password {string}")
    public void i_input_password(String password) {
        // Write code here that turns the phrase above into concrete actions
        bbcSite.bbcSignInPage().inputPassword(password);
    }

    @Then("I receive an error of {string}")
    public void i_receive_an_error_of(String errorMsg) {
        bbcSite.bbcSignInPage().clicksubmitButton();
        Assert.assertEquals(errorMsg, bbcSite.bbcSignInPage().getPasswordErrorText());
    }


    @Then("I receive a username error of {string}")
    public void i_receive_an_UsernameErrorOf(String arg0) {
        bbcSite.bbcSignInPage().clicksubmitButton();
        Assert.assertEquals(arg0, bbcSite.bbcSignInPage().emailErrorText());
    }

    //@Then("I Close the window")
    @After
    public void iCloseTheWindow() {
        bbcSite.quit();
    }
}