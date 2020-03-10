Feature: Different options in Free CRM Login

  @SmokeTest
  Scenario: S1 : Choose different options Test Scenario
    Given User is already on login page
    When title of the page is Free CRM
    Then user enters username and password
    Then user clicks on the login button
    Then user in on the home page
    Then user chooses different options
    Then user logs out of the Application