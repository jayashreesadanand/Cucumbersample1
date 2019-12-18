package step_defs;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;


public class CucumberExampleStepDefs {

    int capturedInt;
    double capturedDouble;
    String capturedWord;

    @Given("I provide an integer of {int}")
    public void i_provide_an_integer_of(int capturedInt) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        this.capturedInt = capturedInt;
    }

    @Then("^I will be able to test the integer$")
    public void i_will_be_able_to_test_the_integer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(14, capturedInt);
    }

    //@Given("^I provide an float of (\\d+)\\.(\\d+)$")
    @Given("I provide an float of {float}")
    public void i_provide_an_float_of(double arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        capturedDouble = arg1;
    }

    @Then("^I will be able to test the float$")
    public void i_will_be_able_to_test_the_float() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(21.5d, capturedDouble,0.0);
    }

    @Given("I provide specific word {word}")
    public void i_provide_specific_word_banana(String capturedWord) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        this.capturedWord = capturedWord;
    }

    @Then("^I will be able to test the word$")
    public void i_will_be_able_to_test_the_word() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals("banana", capturedWord);
    }


}
