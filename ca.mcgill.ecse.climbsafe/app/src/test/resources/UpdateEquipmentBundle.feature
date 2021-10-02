Feature: Update Equipment Bundle (p8)
  As an administrator, I wish to update existing equipment bundles so that I can keep climbers up to date.

  Background: 
    Given the following ClimbSafe system exists: (p8)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |      20 |                  50 |
    Given the following equipment exists in the system: (p8)
      | name           | weight | pricePerWeek |
      | rope           |   3540 |            5 |
      | pickaxe        |    907 |           50 |
      | portable stove |    340 |          100 |
      | boots          |    850 |           10 |
      | backpack       |    500 |           20 |
    Given the following equipment bundles exist in the system: (p8)
      | name         | discount | items                       | quantities |
      | small bundle |       10 | rope,pickaxe                |        2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |      2,1,1 |

  Scenario Outline: Successfully update an equipment bundle without changing name
    When the administrator attempts to update the equipment bundle "<name>" to have name "<name>", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the equipment bundle "<name>" shall contain the items "<items>" with quantities "<quantities>" (p8)
    Then the equipment bundle "<name>" shall have a discount of "<discount>" (p8)

    Examples: 
      | name         | discount | items                                | quantities |
      | small bundle |       20 | backpack,rope,pickaxe                |      1,2,1 |
      | large bundle |       50 | backpack,rope,pickaxe,portable stove |    1,2,1,1 |

  Scenario Outline: Successfully update an equipment bundle, including changing name
    When the administrator attempts to update the equipment bundle "<oldName>" to have name "<newName>", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the equipment bundle "<oldName>" shall not exist in the system (p8)
    Then the equipment bundle "<newName>" shall contain the items "<items>" with quantities "<quantities>" (p8)
    Then the equipment bundle "<newName>" shall have a discount of "<discount>" (p8)

    Examples: 
      | oldName      | newName              | discount | items                                | quantities |
      | small bundle | small bundle special |       20 | backpack,rope,pickaxe                |      1,2,1 |
      | large bundle | large bundle special |       50 | backpack,rope,pickaxe,portable stove |    1,2,1,1 |

  Scenario Outline: Update an equipment bundle with invalid parameters without changing name
    When the administrator attempts to update the equipment bundle "small bundle" to have name "small bundle", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the number of pieces of equipment in the system shall be "5" (p8)
    Then the equipment bundle "small bundle" shall contain the items "rope,pickaxe" with quantities "2,1" (p8)
    Then the equipment bundle "small bundle" shall have a discount of "10" (p8)
    Then the error "<error>" shall be raised (p8)

    Examples: 
      | discount | items                       | quantities | error                                                                  |
      |       35 | boots,coat                  |        1,1 | Equipment coat does not exist                                          |
      |        0 |                             |            | Equipment bundle must contain at least two distinct types of equipment |
      |       25 | rope                        |          2 | Equipment bundle must contain at least two distinct types of equipment |
      |       15 | rope,rope                   |        2,1 | Equipment bundle must contain at least two distinct types of equipment |
      |        0 | rope,portable stove         |       -1,1 | Each bundle item must have quantity greater than or equal to 1         |
      |        0 | rope,pickaxe                |        1,0 | Each bundle item must have quantity greater than or equal to 1         |
      |       -1 | rope,portable stove         |        2,1 | Discount must be at least 0                                            |
      |      101 | rope,pickaxe,portable stove |      2,1,1 | Discount must be no more than 100                                      |

  Scenario Outline: Update an equipment bundle with invalid parameters, including changing name
    When the administrator attempts to update the equipment bundle "small bundle" to have name "<name>", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the number of pieces of equipment in the system shall be "5" (p8)
    Then the equipment bundle "<name>" shall not exist in the system (p8)
    Then the equipment bundle "small bundle" shall contain the items "rope,pickaxe" with quantities "2,1" (p8)
    Then the equipment bundle "small bundle" shall have a discount of "10" (p8)
    Then the error "<error>" shall be raised (p8)

    Examples: 
      | name                 | discount | items                       | quantities | error                                                                  |
      | clothing bundle      |       35 | boots,coat                  |        1,1 | Equipment coat does not exist                                          |
      | empty bundle         |        0 |                             |            | Equipment bundle must contain at least two distinct types of equipment |
      | tiny bundle          |       25 | rope                        |          2 | Equipment bundle must contain at least two distinct types of equipment |
      | small bundle special |       15 | rope,rope                   |        2,1 | Equipment bundle must contain at least two distinct types of equipment |
      | empty bundle         |        0 | rope,portable stove         |       -1,1 | Each bundle item must have quantity greater than or equal to 1         |
      | empty bundle         |        0 | rope,pickaxe                |        1,0 | Each bundle item must have quantity greater than or equal to 1         |
      | small bundle special |       -1 | rope,pickaxe                |        2,1 | Discount must be at least 0                                            |
      | big bundle           |      101 | rope,pickaxe,portable stove |      2,1,1 | Discount must be no more than 100                                      |
      | pickaxe              |       20 | pickaxe,rope                |        1,1 | A bookable item called pickaxe already exists                          |
      |                      |        0 | rope,pickaxe                |        1,1 | Equipment bundle name cannot be empty                                  |

  Scenario: Update an equipment bundle using a name that already exists
    When the administrator attempts to update the equipment bundle "small bundle" to have name "large bundle", discount "30", items "boots,backpack,portable stove", and quantities "1,1,1" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the equipment bundle "large bundle" shall contain the items "rope,pickaxe,portable stove" with quantities "2,1,1" (p8)
    Then the equipment bundle "large bundle" shall have a discount of "25" (p8)
    Then the equipment bundle "small bundle" shall contain the items "rope,pickaxe" with quantities "2,1" (p8)
    Then the equipment bundle "small bundle" shall have a discount of "10" (p8)
    Then the error "A bookable item called large bundle already exists" shall be raised (p8)

  Scenario Outline: Update an equipment bundle that does not exist
    When the administrator attempts to update the equipment bundle "<oldName>" to have name "<newName>", discount "<discount>", items "<items>", and quantities "<quantities>" (p8)
    Then the number of equipment bundles in the system shall be "2" (p8)
    Then the number of pieces of equipment in the system shall be "5" (p8)
    Then the equipment bundle "<name>" shall not exist in the system (p8)
    Then the error "<error>" shall be raised (p8)

    Examples: 
      | oldName    | newName         | discount | items          | quantities | error                                      |
      | new bundle | clothing bundle |       10 | boots,backpack |        1,1 | Equipment bundle new bundle does not exist |
      | rope       | clothing bundle |       10 | boots,backpack |        1,1 | Equipment bundle rope does not exist       |
