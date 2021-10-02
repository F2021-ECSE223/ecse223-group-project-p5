Feature: Setup NMC program information (p10)
  As the administrator, I want to setup the NMC program information

  Background: 
    Given a ClimbSafe system exists with defaults values "2020-12-31" as start date, "0" for number of weeks, and "0" for price of guide per week. (p10)

  Scenario Outline: Setup NMC program information successfully
    When the admin attempts to setup the NMC program information with the start date "2022-01-13", number of climbing weeks "20", and price of guide per week "50" (p10)
    Then the NMC program information shall be successfully setup with the start date "2022-01-13", number of climbing weeks "20", and price of guide per week "50" (p10)

  Scenario Outline: Setup NMC program information with invalid inputs
    When the admin attempts to setup the NMC program inforamtion with the start date "<startDate>", number of climbing weeks "<nrWeeks>", and price of guide per week "<priceOfGuidePerWeek>" (p10)
    Then the following "<error>" shall be raised. (p10)

    Examples: 
      | startDate  | nrWeeks | priceOfGuidePerWeek | error                                                              |
      | 2022-01-13 |       0 |                  50 | The number of climbing weeks must be greater than or equal to zero |
      | 2022-01-13 |      20 |                   0 | The price of guide per week must be greater than or equal to zero  |
      | 2021-31-31 |      20 |                  50 | Invalid date                                                       |
