Feature: Delete Hotel (p16)
  As an administrator, I want to delete an existing hotel in the system so that I can restrict the hotels available to climbers.

  Background: 
    Given the following ClimbSafe system exists: (p16)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |      20 |                  50 |
    Given the following hotels exist in the system: (p16)
      | name                    | address                  | rating |
      | Brian's Bed & Breakfast | 123 Crescent St., Neptan |      2 |
      | The Ritz Carlton        | 456 Main St., Neptan     |      5 |

  Scenario: Successfully delete a hotel
    When the administator attempts to delete the hotel in the system with name "Brian's Bed & Breakfast" (p16)
    Then the number of hotels in the system shall be "1" (p16)
    Then the hotel with name "Brian's Bed & Breakfast", address "123 Crescent St., Neptan", and rating "2" shall not exist in the system (p16)
    Then the following hotels shall exist in the system: (p16)
      | name             | address              | rating |
      | The Ritz Carlton | 456 Main St., Neptan |      5 |

  Scenario: Delete a hotel that does not exist in the system
    When the administator attempts to delete the hotel in the system with name "Sofitel" (p16)
    Then the number of hotels in the system shall be "2" (p16)
    Then the following hotels shall exist in the system: (p16)
      | name                    | address                  | rating |
      | Brian's Bed & Breakfast | 123 Crescent St., Neptan |      2 |
      | The Ritz Carlton        | 456 Main St., Neptan     |      5 |
