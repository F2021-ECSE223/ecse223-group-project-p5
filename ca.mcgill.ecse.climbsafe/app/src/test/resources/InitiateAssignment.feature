Feature: Initiate assignment
  As an administrator, I wish to initiate assignments to assign mountain guides and climbing weeks to members

  # For all scenarios, members and guides are registered in the system following the order listed in their respective tables (top to bottom)

  Background:
    Given the following ClimbSafe system exists:
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 | 5       | 50                  |
    Given the following pieces of equipment exist in the system:
      | name           | weight | pricePerWeek |
      | pickaxe        | 430    | 25           |
      | rope           | 57     | 8            |
      | portable stove | 85     | 17           |
    Given the following equipment bundles exist in the system:
      | name         | discount | items                       | quantity |
      | small bundle | 10       | rope,pickaxe                | 2,1      |
      | large bundle | 25       | rope,pickaxe,portable stove | 2,1,1    |

  Scenario: Successfully initiate an assignment in which a newer member is assigned before an older one
    Given the following guides exist in the system:
      | email        | password  | name         | emergencyContact |
      | bob@nmc.nt   | passw0rd1 | Bob Smith    | 2005555678       |
      | diana@nmc.nt | diana1991 | Diana Prince | 2005555432       |
    Given the following members exist in the system:
      | email              | password | name             | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems          | bookedItemQuantities |
      | alice@gmail.com    | pass123  | Alice Jones      | 2005551234       | 3       | true          | false         | small bundle,pickaxe | 2,1                  |
      | charlie@hotmail.ca | charlie  | Charles Tremblay | 2005559876       | 3       | false         | true          | large bundle         | 2                    |
      | john@hotmail.ca    | john123  | John Doe         | 2005551234       | 3       | true          | false         | small bundle,rope    | 2,1                  |
      | emily@hotmail.ca   | emily007 | Emily Green      | 2005559876       | 2       | true          | true          | large bundle         | 2                    |
    When the administrator attempts to initiate the assignment process
    Then the following assignments shall exist in the system:
      | memberEmail        | guideEmail   | startWeek | endWeek |
      | alice@gmail.com    | bob@nmc.nt   | 1         | 3       |
      | charlie@hotmail.ca |              | 1         | 3       |
      | emily@hotmail.ca   | bob@nmc.nt   | 4         | 5       |
      | john@hotmail.ca    | diana@nmc.nt | 1         | 3       |
    Then the assignment for "alice@gmail.com" shall be marked as "Assigned"
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Assigned"
    Then the assignment for "emily@hotmail.ca" shall be marked as "Assigned"
    Then the assignment for "john@hotmail.ca" shall be marked as "Assigned"
    Then the number of assignments in the system shall be "4"

  Scenario: Successfully initiate an assignment requiring a single guide
    Given the following guides exist in the system:
      | email        | password  | name         | emergencyContact |
      | bob@nmc.nt   | passw0rd1 | Bob Smith    | 2005555678       |
      | diana@nmc.nt | diana1991 | Diana Prince | 2005555432       |
    Given the following members exist in the system:
      | email              | password | name             | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems          | bookedItemQuantities |
      | alice@gmail.com    | pass123  | Alice Jones      | 2005551234       | 1       | true          | false         | small bundle,pickaxe | 2,1                  |
      | charlie@hotmail.ca | charlie  | Charles Tremblay | 2005559876       | 3       | false         | true          | large bundle         | 2                    |
      | john@hotmail.ca    | john123  | John Doe         | 2005551234       | 3       | true          | false         | small bundle,rope    | 2,1                  |
    When the administrator attempts to initiate the assignment process
    Then the following assignments shall exist in the system:
      | memberEmail        | guideEmail | startWeek | endWeek |
      | alice@gmail.com    | bob@nmc.nt | 1         | 1       |
      | charlie@hotmail.ca |            | 1         | 3       |
      | john@hotmail.ca    | bob@nmc.nt | 2         | 4       |
    Then the assignment for "alice@gmail.com" shall be marked as "Assigned"
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Assigned"
    Then the assignment for "john@hotmail.ca" shall be marked as "Assigned"
    Then the number of assignments in the system shall be "3"

  Scenario: Successfully initiate an assignment requiring all guides
    Given the following guides exist in the system:
      | email        | password  | name         | emergencyContact |
      | bob@nmc.nt   | passw0rd1 | Bob Smith    | 2005555678       |
      | diana@nmc.nt | diana1991 | Diana Prince | 2005555432       |
    Given the following members exist in the system:
      | email              | password | name             | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems          | bookedItemQuantities |
      | alice@gmail.com    | pass123  | Alice Jones      | 2005551234       | 3       | true          | false         | small bundle,pickaxe | 2,1                  |
      | charlie@hotmail.ca | charlie  | Charles Tremblay | 2005559876       | 2       | false         | true          | large bundle         | 2                    |
      | john@hotmail.ca    | john123  | John Doe         | 2005551234       | 4       | true          | false         | small bundle,rope    | 2,1                  |
    When the administrator attempts to initiate the assignment process
    Then the following assignments shall exist in the system:
      | memberEmail        | guideEmail   | startWeek | endWeek |
      | alice@gmail.com    | bob@nmc.nt   | 1         | 3       |
      | charlie@hotmail.ca |              | 1         | 2       |
      | john@hotmail.ca    | diana@nmc.nt | 1         | 4       |
    Then the assignment for "alice@gmail.com" shall be marked as "Assigned"
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Assigned"
    Then the assignment for "john@hotmail.ca" shall be marked as "Assigned"
    Then the number of assignments in the system shall be "3"


  Scenario: Successfully initiate an assignment in which members are assigned to the first guide checked
    Given the following guides exist in the system:
      | email        | password  | name         | emergencyContact |
      | bob@nmc.nt   | passw0rd1 | Bob Smith    | 2005555678       |
      | diana@nmc.nt | diana1991 | Diana Prince | 2005555432       |
    Given the following members exist in the system:
      | email              | password | name             | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems          | bookedItemQuantities |
      | alice@gmail.com    | pass123  | Alice Jones      | 2005551234       | 3       | true          | false         | small bundle,pickaxe | 2,1                  |
      | charlie@hotmail.ca | charlie  | Charles Tremblay | 2005559876       | 3       | false         | true          | large bundle         | 2                    |
      | john@hotmail.ca    | john123  | John Doe         | 2005551234       | 2       | true          | false         | small bundle,rope    | 2,1                  |
      | emily@hotmail.ca   | emily007 | Emily Green      | 2005559876       | 3       | true          | true          | large bundle         | 2                    |
    When the administrator attempts to initiate the assignment process
    Then the following assignments shall exist in the system:
      | memberEmail        | guideEmail   | startWeek | endWeek |
      | alice@gmail.com    | bob@nmc.nt   | 1         | 3       |
      | charlie@hotmail.ca |              | 1         | 3       |
      | john@hotmail.ca    | bob@nmc.nt   | 4         | 5       |
      | emily@hotmail.ca   | diana@nmc.nt | 1         | 3       |
    Then the assignment for "alice@gmail.com" shall be marked as "Assigned"
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Assigned"
    Then the assignment for "emily@hotmail.ca" shall be marked as "Assigned"
    Then the assignment for "john@hotmail.ca" shall be marked as "Assigned"
    Then the number of assignments in the system shall be "4"


  Scenario: Unsuccessfully initiate a complete assignment and instead provide a partial assignment
    Given the following guides exist in the system:
      | email        | password  | name         | emergencyContact |
      | bob@nmc.nt   | passw0rd1 | Bob Smith    | 2005555678       |
      | diana@nmc.nt | diana1991 | Diana Prince | 2005555432       |
    Given the following members exist in the system:
      | email              | password | name             | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems          | bookedItemQuantities |
      | alice@gmail.com    | pass123  | Alice Jones      | 2005551234       | 3       | true          | false         | small bundle,pickaxe | 2,1                  |
      | charlie@hotmail.ca | charlie  | Charles Tremblay | 2005559876       | 3       | false         | true          | large bundle         | 2                    |
      | john@hotmail.ca    | john123  | John Doe         | 2005551234       | 4       | true          | false         | small bundle,rope    | 2,1                  |
      | emily@hotmail.ca   | emily007 | Emily Green      | 2005559876       | 3       | true          | true          | large bundle         | 2                    |
    When the administrator attempts to initiate the assignment process
    Then the following assignments shall exist in the system:
      | memberEmail        | guideEmail   | startWeek | endWeek |
      | alice@gmail.com    | bob@nmc.nt   | 1         | 3       |
      | charlie@hotmail.ca |              | 1         | 3       |
      | john@hotmail.ca    | diana@nmc.nt | 1         | 4       |
    Then the assignment for "alice@gmail.com" shall be marked as "Assigned"
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Assigned"
    Then the assignment for "john@hotmail.ca" shall be marked as "Assigned"
    Then the number of assignments in the system shall be "3"
    Then the system shall raise the error "Assignments could not be completed for all members"