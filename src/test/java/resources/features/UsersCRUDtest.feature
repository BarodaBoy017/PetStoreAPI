@TestAPI
Feature: Testing a REST API
  Users should be able to submit GET and POST requests to PetStoreAPI

  Scenario: User able to add pet
    When User upload data on a project by submitting POST request on "/pet" endpoint
    Then The server should handle it and return a success status and valid Response data.

  Scenario: User able to fetch pet data
    When User want to get information  by submitting GET request on "/pet/{petID}" endpoint
    Then The requested data is returned successfully

  Scenario: User should not able to add pet without payload
    When User upload empty data on a project by submitting POST request on "/pet" endpoint
    Then The server should handle it and return a message with 405 status code.

  Scenario: User should not able to add pet with invalid payload/body
    When User pass invalid body on a project by submitting POST request on "/pet" endpoint
    Then The server should handle it and return a error message with 400 status code.
