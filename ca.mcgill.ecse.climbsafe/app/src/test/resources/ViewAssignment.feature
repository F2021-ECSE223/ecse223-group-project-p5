Feature: View Assignments (p5)
  As an administrator, I wish to view the assignments for each member so that I can keep track of climbers' assignments.

  Background: 
    Given the following ClimbSafe system exists: (p5)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |      20 |                  50 |
    Given the following hotels exist in the system: (p5)
      | name                      | address                    | rating |
      | "Brian's Bed & Breakfast" | "123 Crescent St., Neptan" |      2 |
      | The Ritz Carlton          | "456 Main St., Neptan"     |      5 |
    Given the following guides exist in the system: (p5)
      | email        | password  | name         | emergencyContact |
      | bob@nmc.nt   | passw0rd1 | Bob Smith    |       2005555678 |
      | diana@nmc.nt | diana1991 | Diana Prince |       2005555432 |
    Given the following equipment exists in the system: (p5)
      | name           | weight | pricePerWeek |
      | rope           |   3540 |            5 |
      | pickaxe        |    907 |           50 |
      | portable stove |    340 |          100 |
      | backpack       |    500 |           20 |
    Given the following equipment bundles exist in the system: (p5)
      | name         | discount | items                       | quantities |
      | small bundle |       10 | rope,pickaxe                |        2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |      2,1,1 |

  Scenario: Successfully view assignments for 0 members
    # It is implied that there are no members and no assignments at this point
    When the administrator attempts to view the assignments (p5)
    Then the number of assignments displayed shall be "0" (p5)

  # NOTE: An empty guide/hotel name means that no guide/hotel is assigned
  Scenario: Successfully view assignment for 1 member
    Given the following members exist in the system: (p5)
      | email           | password | name        | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems           | bookedItemQuantities |
      | alice@gmail.com | pass123  | Alice Jones |       2005551234 |       4 | true          | false         | small bundle,backpack |                  1,1 |
    Given the following assignments exist in the system: (p5)
      | memberEmail     | guideEmail | hotelName | startWeek | endWeek |
      | alice@gmail.com | bob@nmc.nt |           |         2 |       5 |
    When the administrator attempts to view the assignments (p5)
    Then the number of assignments displayed shall be "1" (p5)
    Then the following assignment information shall be displayed: (p5)
      | memberEmail     | memberName  | guideEmail | guideName | hotelName | startWeek | endWeek | totalCostForGuide | totalCostForEquipment |
      | alice@gmail.com | Alice Jones | bob@nmc.nt | Bob Smith |           |         2 |       5 |               200 |                   296 |

  # NOTE: An empty guide/hotel name means that no guide/hotel is assigned
  Scenario: Successfully view assignments for 4 members
    Given the following members exist in the system: (p5)
      | email               | password  | name             | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems               | bookedItemQuantities |
      | alice@gmail.com     | pass123   | Alice Jones      |       2005551234 |       4 | true          | false         | small bundle,backpack     |                  2,1 |
      | charlie@hotmail.ca  | charlie   | Charles Tremblay |       2005559876 |       3 | false         | true          | large bundle              |                    2 |
      | emma@yahoo.com      | "emm@123" | Emma Davis       |       2005553141 |      10 | true          | true          | large bundle,rope,pickaxe |                1,3,1 |
      | mohamed@outlook.com | pass123   | Mohamed Farah    |       2005552718 |      20 | false         | false         |                           |                      |
    Given the following assignments exist in the system: (p5)
      | memberEmail         | guideEmail   | hotelName                 | startWeek | endWeek |
      | alice@gmail.com     | bob@nmc.nt   |                           |         2 |       5 |
      | charlie@hotmail.ca  |              | "Brian's Bed & Breakfast" |         3 |       5 |
      | emma@yahoo.com      | diana@nmc.nt | The Ritz Carlton          |        10 |      19 |
      | mohamed@outlook.com |              |                           |         1 |      20 |
    When the administrator attempts to view the assignments (p5)
    Then the number of assignments displayed shall be "4" (p5)
    Then the following assignment information shall be displayed: (p5)
      | memberEmail         | memberName       | guideEmail   | guideName    | hotelName                 | startWeek | endWeek | totalCostForGuide | totalCostForEquipment |
      | alice@gmail.com     | Alice Jones      | bob@nmc.nt   | Bob Smith    |                           |         2 |       5 |               200 |                   512 |
      | charlie@hotmail.ca  | Charles Tremblay |              |              | "Brian's Bed & Breakfast" |         3 |       5 |                 0 |                   960 |
      | emma@yahoo.com      | Emma Davis       | diana@nmc.nt | Diana Prince | The Ritz Carlton          |        10 |      19 |               500 |                  1850 |
      | mohamed@outlook.com | Mohamed Farah    |              |              |                           |         1 |      20 |                 0 |                     0 |
