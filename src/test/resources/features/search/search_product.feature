Feature: Search for the product

  Scenario Outline: Verify search results for available products
    Given 'James' wants to buy an item
    When he performs search for an '<item>'
    Then he sees the results are displayed for '<item>'

    Examples:
      | item        |
      | apple       |
      | orange      |
      | pasta       |
      | cola        |

  Scenario Outline: Verify search results for unavailable products
    Given 'George' wants to buy an item
    When he performs search for an '<item>'
    Then he doesn't see the results
    And '<status code>' and '<error message>' are shown for an '<item>'

    Examples:
      | item | status code | error message      |
      | car  | 404         | Not found          |
      |      | 404         | Not found          |

  Scenario Outline: Verify search endpoint for not allowed methods
    Given 'Elizabeth' wants to buy an item
    When she performs search for an '<item>' using http '<method>'
    Then she doesn't see the results
    And '<status code>' and '<error message>' are shown for an '<item>'

    Examples:
      | item   | method  | status code | error message      |
      | apple  | POST    | 405         | Method Not Allowed |
      | orange | PUT     | 405         | Method Not Allowed |
      | pasta  | DELETE  | 405         | Method Not Allowed |
      | cola   | PATCH   | 405         | Method Not Allowed |