Feature: Delete Equipment (p7)
  As an administrator, I want to delete existing equipment in the system so that I can restrict the equipment available to climbers.

  Background: 
    Given the following ClimbSafe system exists: (p7)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |      20 |                  50 |
    Given the following pieces of equipment exist in the system: (p7)
      | name           | weight | pricePerWeek |
      | crampons       |    900 |           11 |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |
    Given the following equipment bundles exist in the system: (p7)
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |

  Scenario: Successfully delete a piece of equipment
    When the administator attempts to delete the piece of equipment in the system with name "crampons" (p7)
    Then the number of pieces of equipment in the system shall be "3" (p7)
    Then the piece of equipment with name "crampons", weight "900", and price per week "11" shall not exist in the system (p7)
    Then the following pieces of equipment shall exist in the system: (p7)
      | name           | weight | pricePerWeek |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |

  Scenario: Delete equipment that has been requested by a member
    Given the following members exist in the system: (p7)
      | email             | password | name  | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems   | quantity |
      | member1@email.com | pass1    | Peter | (666)555-5555    |       1 | true          | true          | crampons,rope |      1,2 |
    When the administator attempts to delete the piece of equipment in the system with name "crampons" (p7)
    Then the number of booked items for the member with email "member1@email.com" shall be "1" (p7)
    Then the member with email "member1@email.com" shall have a bookable item with name "rope" and quantity "2" (p7)
    Then the number of pieces of equipment in the system shall be "3" (p7)
    Then the piece of equipment with name "crampons", weight "900", and price per week "11" shall not exist in the system (p7)
    Then the following pieces of equipment shall exist in the system: (p7)
      | name           | weight | pricePerWeek |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |

  Scenario Outline: Delete a piece of equipment that does not exist in the system
    When the administator attempts to delete the piece of equipment in the system with name "<target>" (p7)
    Then the number of pieces of equipment in the system shall be "4" (p7)
    Then the following pieces of equipment shall exist in the system: (p7)
      | name           | weight | pricePerWeek |
      | crampons       |    900 |           11 |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |
    Then the number of equipment bundles in the system shall be "2" (p7)
    Then the following equipment bundles shall exist in the system: (p7)
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |

    Examples: 
      | target       |
      | harness      |
      | small bundle |

  Scenario: Unsuccessfully delete a piece of equipment that is in an existing bundle
    When the administator attempts to delete the piece of equipment in the system with name "pickaxe" (p7)
    Then the number of pieces of equipment in the system shall be "4" (p7)
    Then the following pieces of equipment shall exist in the system: (p7)
      | name           | weight | pricePerWeek |
      | crampons       |    900 |           11 |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |
    Then the number of equipment bundles in the system shall be "2" (p7)
    Then the following equipment bundles shall exist in the system: (p7)
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |
    Then the system shall raise the error "The piece of equipment is in a bundle and cannot be deleted" (p7)
