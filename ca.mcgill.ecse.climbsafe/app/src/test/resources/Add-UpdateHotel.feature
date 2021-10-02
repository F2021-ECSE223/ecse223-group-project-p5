Feature: Add/Update Hotel (p13)
  As an administrator, I wish to add and update Hotel information in the system.

  Background: 
    Given the following ClimbSafe system exists: (p13)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-03-13 |       7 |                 100 |
    Given the following hotels exist in the system: (p13)
      | name       | address       | nrStars |
      | Marriott   | 1 West Avenue |       5 |
      | HolidayInn | 1 East Avenue |       4 |

  Scenario Outline: Add a hotel successfully
    When the admin attempts to add a new hotel to the system with "<name>", "<address>", and "<nrStars>" (p13)
    Then the hotel with "<name>", "<address>", and "<nrStars>" shall now exist in the system (p13)
    Then the number of hotels in the system is 3 (p13)

    Examples: 
      | name      | address        | nrStars |
      | Kempinski | 1 South Avenue |       5 |
      | Hilton    | 1 North Avenue |       4 |

  Scenario Outline: Add a hotel unsuccesfully
    When the admin attempts to add a new hotel to the system with "<name>", "<address>", and "<nrStars>" (p13)
    Then the following "<error>" shall be raised (p13)
    Then the hotel with "<name>" shall "<target>" in the system (p13)
    Then the number of hotels in the system is 2 (p13)

    Examples: 
      | name       | address         | nrStars | error                                   | target    |
      |            | 4 Center Avenue |       3 | Name cannot be empty                    | not exist |
      | Hyaat      |                 |       4 | Address cannot be empty                 | not exist |
      | WesternInn | 4 Center Avenue |      -1 | Number of stars must be between 1 and 5 | not exist |
      | WesternInn | 4 Center Avenue |       0 | Number of stars must be between 1 and 5 | not exist |
      | WesternInn | 4 Center Avenue |       6 | Number of stars must be between 1 and 5 | not exist |
      | Marriott   | 4 Center Avenue |       3 | Hotel already exists in the system      | exist     |
      | HolidayInn | 1 East Avenue   |       4 | Hotel already exists in the system      | exist     |

  Scenario Outline: Update a hotel successfully
    When the admin attempts to update hotel with "<name>" in the system to have a "<newName>", "<newAddress>", and "<newNrStars>" (p13)
    Then the hotel will be updated to have a "<newName>", "<newAddress>", and "<newNrStars>" (p13)
    Then the number of hotels in the system is 2 (p13)

    Examples: 
      | name       | newName            | newAddress         | newNrStars |
      | Marriott   | JW Marriot         | 100 Ave. Boulevard |          4 |
      | HolidayInn | HolidayInn Express | 25 Prince St.      |          5 |

  Scenario Outline: Update a hotel unsuccessfully
    When the admin attempts to update hotel with "<name>" in the system to have a "<newName>", "<newAddress>", and "<newNrStars>" (p13)
    Then the following "<error>" shall be raised (p13)
    Then the hotel will keep its "<name>", "<oldAddress>", and "<oldNrStars>" (p13)
    Then the number of hotels in the system is 2 (p13)

    Examples: 
      | name       | newName    | newAddress       | newNrStars | error                                    | oldAddress    | oldNrStars |
      | Marriott   |            | 45 Center Avenue |          3 | Name cannot be empty                     | 1 West Avenue |          5 |
      | Marriott   | Marriott   |                  |          5 | Address cannot be empty                  | 1 West Avenue |          5 |
      | HolidayInn | HolidayInn | 55 West Avenue   |         -1 | Number of stars must be between 1 and 5  | 1 East Avenue |          4 |
      | HolidayInn | HolidayInn | 55 West Avenue   |          0 | Number of stars must be between 1 and 5  | 1 East Avenue |          4 |
      | HolidayInn | HolidayInn | 55 West Avenue   |          6 | Number of stars must be between 1 and 5  | 1 East Avenue |          4 |
      | Marriott   | HolidayInn | 1 West Avenue    |          5 | New name already linked to another hotel | 1 West Avenue |          5 |

  Scenario Outline: Update a hotel which does not exist in the system
    When the admin attempts to update hotel with "<name>" in the system to have a "<newName>", "<newAddress>", and "<newNrStars>" (p13)
    Then the following error "Hotel does not exist in the system" shall be raised (p13)
    Then the number of hotels in the system is 2 (p13)

    Examples: 
      | name       | newName      | newAddress       | newNrStars |
      | Carlton    | Carlton Ritz | 45 Center Avenue |          4 |
      | WesternInn | WesternInn   | 54 Center Avenue |          5 |
