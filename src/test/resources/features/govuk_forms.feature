Feature: Access to GOV.UK Forms from the service
  As a registered user
  I want to access custom GOV.UK forms
  So that I can complete and submit required information

  Scenario: A user can navigate to the forms access page
    Given I navigate to the GOV.UK service home page
    When I click the "Access Forms" button
    Then I should see the forms page heading "Access your forms"
    And I should see a link to GOV.UK Forms

  Scenario: The forms page shows a GOV.UK Forms link after registration
    Given I am on the registration confirmation page for "Test User"
    When I click the "Access Forms" button
    Then I should see the forms page heading "Access your forms"
