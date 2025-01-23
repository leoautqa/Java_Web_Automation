@regression @amazon
Feature: amazon

  Background: 
    Given Be on the amazon page

	@tc01
  Scenario: 01 Add product to cart
    Given Look for either 'When: The Scientific Secrets of Perfect Timing', 'Nick Page'
    And Filter products found by Books
    When Open a book page
    And Add 2 item to cart
    Then the cart subtotal must be less than 100 dollars

	@tc02
  Scenario: 02 Search for a book
    Given Look for either 'Jorge Amado', 'Nick Page'
    And Filter products found by Books
    When Open a book page
    Then Confirm at least 70% of people liked the book with 5 stars