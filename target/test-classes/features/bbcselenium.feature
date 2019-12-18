Feature: Basic selenium tests on the BBC website
  As a user of cucumber
  I want to gain a better understanding of feature files and selenium
  So I can build my own automated test scripts

  Scenario: A user can access the home page and click the sign in link
    Given I can access the bbc home page
    When I click the sign in link
    Then I should access the sign in page


  Scenario: I can input name and password details that receives an error for lack of letters
    Given I am on the Sign-In page
    When I input username "Dave@mail.com"
    And I input password "abcd"
    Then I receive an error of "Sorry, that password is too short. It needs to be eight characters or more."


  Scenario: I can input an invalid email id and an error for incorrect format
    Given I am on the Sign-In page
    When I input username "Davemail.com"
    And I input password "abcd"
    Then I receive a username error of "Usernames can only include... Letters, numbers and these characters: ?/|}{+=_-^~`%$#"


