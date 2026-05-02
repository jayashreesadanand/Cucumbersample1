Feature: User registration on the GOV.UK service
  As a new user
  I want to register for the GOV.UK service
  So that I can access custom forms

  Scenario: A user can load the registration page
    Given I navigate to the GOV.UK service home page
    When I click the "Register" button
    Then I should see the registration form with a "Full name" field

  Scenario: A user registers successfully with valid details
    Given I am on the registration page
    When I enter full name "Jane Smith"
    And I enter email address "jane.smith@example.gov.uk"
    And I enter password "SecurePass1"
    And I submit the registration form
    Then I should see the registration confirmation page
    And the confirmation page should show "Jane Smith"

  Scenario: A user sees validation errors when submitting an empty form
    Given I am on the registration page
    When I submit the registration form
    Then I should see an error summary
    And I should see the error "Enter your full name"
    And I should see the error "Enter a valid email address"
    And I should see the error "Password must be at least 8 characters"

  Scenario: A user sees a validation error for an invalid email address
    Given I am on the registration page
    When I enter full name "John Doe"
    And I enter email address "not-an-email"
    And I enter password "SecurePass1"
    And I submit the registration form
    Then I should see the error "Enter a valid email address"

  Scenario: A user sees a validation error for a short password
    Given I am on the registration page
    When I enter full name "John Doe"
    And I enter email address "john.doe@example.gov.uk"
    And I enter password "short"
    And I submit the registration form
    Then I should see the error "Password must be at least 8 characters"
