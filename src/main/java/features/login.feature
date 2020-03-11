Feature: Free CRM login feature

  @SmokeTest
  Scenario Outline: S1 : Create contact Test Scenario
    Given User is already on login page
    When title of the page is Free CRM
    Then user enters username and password
    Then user clicks on the login button
    Then user in on the home page
    Then user creates a new Contact
    Then user enters "<Firstname>" , "<Lastname>" and "<Category>" .
    Then user removes the contact and close the browser
    
    Examples:
    |Firstname|Lastname|Category|
    |Saptarshi|Dasgupta|Lead|
    |Riya|Ghosh|Customer|

@SmokeTest
  Scenario: S2 : Free CRM Login Test Scenario
    Given User is already on login page
    When title of the page is Free CRM
    Then user enters username and password
    Then user clicks on the login button
    Then user in on the home page
    Then user logs out of the Application