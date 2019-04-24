Feature: adding a company

  Scenario: user adds a company

    Given a company
    When a user sends a post
    Then the status should be 200