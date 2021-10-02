Feature: Update Equipment (p1)
  As an administrator, I want to update existing equipment in the system so that I can reflect changes to the equipment available to climbers.

  Background: 
    Given the following ClimbSafe system exists: (p1)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |      20 |                  50 |
    Given the following pieces of equipment exist in the system: (p1)
      | name           | weight | pricePerWeek |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |
    Given the following equipment bundles exist in the system: (p1)
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |

  Scenario Outline: Successfully update all information for a piece of equipment
    When the administator attempts to update the piece of equipment in the system with name "<oldName>" to have name "<newName>", weight "<newWeight>", and price per week "<newPricePerWeek>" (p1)
    Then the number of pieces of equipment in the system shall be "3" (p1)
    Then the piece of equipment with name "<oldName>", weight "<oldWeight>", and price per week "<oldPricePerWeek>" shall not exist in the system (p1)
    Then the piece of equipment with name "<newName>", weight "<newWeight>", and price per week "<newPricePerWeek>" shall exist in the system (p1)

    Examples: 
      | oldName | oldWeight | oldPricePerWeek | newName             | newWeight | newPricePerWeek |
      | pickaxe |       430 |              25 | lightweight pickaxe |       280 |              35 |
      | rope    |        57 |               8 | ultra durable rope  |        63 |              15 |
      | pickaxe |       430 |              25 | pickaxe             |       390 |              25 |

  Scenario Outline: Unsuccessfully update a piece of equipment with invalid information
    When the administator attempts to update the piece of equipment in the system with name "pickaxe" to have name "<newName>", weight "<newWeight>", and price per week "<newPricePerWeek>" (p1)
    Then the number of pieces of equipment in the system shall be "3" (p1)
    Then the following pieces of equipment shall exist in the system: (p1)
      | name           | weight | pricePerWeek |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |
    Then the system shall raise the error "<error>" (p1)

    Examples: 
      | newName            | newWeight | newPricePerWeek | error                                                 |
      | lighweight pickaxe |         0 |              35 | The weight must be greater than 0                     |
      | lighweight pickaxe |      -280 |              35 | The weight must be greater than 0                     |
      | lighweight pickaxe |       280 |             -35 | The price per week must be greater than or equal to 0 |
      |                    |       280 |              35 | The name must not be empty                            |

  Scenario Outline: Unsuccessfully update a piece of equipment that does not exist in the system
    When the administator attempts to update the piece of equipment in the system with name "crampons" to have name "<newName>", weight "<newWeight>", and price per week "<newPricePerWeek>" (p1)
    Then the number of pieces of equipment in the system shall be "3" (p1)
    Then the following pieces of equipment shall exist in the system: (p1)
      | name           | weight | pricePerWeek |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |
    Then the system shall raise the error "The piece of equipment does not exist" (p1)

    Examples: 
      | newName             | newWeight | newPricePerWeek |
      | adjustable crampons |      1050 |              14 |

  Scenario Outline: Unsuccessfully update a piece of equipment with a name that already exists in the system
    When the administator attempts to update the piece of equipment in the system with name "pickaxe" to have name "<newName>", weight "<newWeight>", and price per week "<newPricePerWeek>" (p1)
    Then the number of pieces of equipment in the system shall be "3" (p1)
    Then the following pieces of equipment shall exist in the system: (p1)
      | name           | weight | pricePerWeek |
      | pickaxe        |    430 |           25 |
      | rope           |     57 |            8 |
      | portable stove |     85 |           17 |
    Then the number of equipment bundles in the system shall be "2" (p1)
    Then the following equipment bundles shall exist in the system: (p1)
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |
    Then the system shall raise the error "<error>" (p1)

    Examples: 
      | newName      | newWeight | newPricePerWeek | error                                                 |
      | rope         |       280 |              35 | The piece of equipment already exists                 |
      | small bundle |       280 |              35 | An equipment bundle with the same name already exists |
