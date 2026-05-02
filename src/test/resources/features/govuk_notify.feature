Feature: GOV.UK Notify sends a confirmation email on user registration
  As a service operator
  I want new users to receive a confirmation email via GOV.UK Notify
  So that they can verify their email address and complete registration

  Scenario: A registration confirmation email is sent after successful registration
    Given the GOV.UK Notify service is configured
    And I am on the registration page
    When I enter full name "Jane Smith"
    And I enter email address "jane.smith@example.gov.uk"
    And I enter password "SecurePass1"
    And I submit the registration form
    Then a confirmation email should be sent to "jane.smith@example.gov.uk"
    And the email should contain the user's name "Jane Smith"

  Scenario: No confirmation email is sent when registration fails validation
    Given the GOV.UK Notify service is configured
    And I am on the registration page
    When I submit the registration form
    Then no confirmation email should be sent
