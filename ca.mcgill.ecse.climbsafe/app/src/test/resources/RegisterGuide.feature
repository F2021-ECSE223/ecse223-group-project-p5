Feature: Register Guide (p3)
  As a guide, I wish to register a guide account in the system

  Background: 
    Given the following ClimbSafe system exists: (p3)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-03-13 |      10 |                 100 |
    Given the following guides exist in the system: (p3)
      | email          | password | name | emergencyContact |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    |
      | john@email.com | pass2    | John | (444)444-4444    |
    Given the following members exist in the system: (p3)
      | email           | password | name  | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookableItem | quantity |
      | peter@email.com | pass1    | peter | (666)555-5555    |       1 | true          | true          |              |          |
      | tyler@email.com | pass2    | tyler | (777)444-4444    |       2 | false         | false         |              |          |

  Scenario Outline: A guide registers successfully
    When a new guide attempts to register with "<email>", "<password>", "<name>", and "<emergencyContact>" (p3)
    Then a new guide account shall exist with "<email>", "<password>", "<name>", and "<emergencyContact>" (p3)
    Then the number of guides in the system is 3 (p3)

    Examples: 
      | email          | password | name | emergencyContact |
      | lisa@email.com | pass1    | Lisa | (666)666-6666    |
      | liam@email.com | pass2    | Liam | (777)777-7777    |

  Scenario Outline: A guide registers unsuccessfully
    When a new guide attempts to register with "<email>", "<password>", "<name>", and "<emergencyContact>" (p3)
    Then the following "<error>" shall be raised (p3)
    Then the number of guides in the system is 2 (p3)

    Examples: 
      | email            | password | name  | emergencyContact | error                                    |
      | admin@nmc.nt     | pass1    | Paul  | (111)111-1111    | Email cannot be admin@nmc.nt             |
      | jeff@email.com   | pass2    | Jeff  | (111)777-7777    | Email already linked to a guide account  |
      | peter@email.com  | pass1    | peter | (666)555-5555    | Email already linked to a member account |
      | bart @ email.com | pass3    | Bart  | (444)666-6666    | Email must not contain any spaces        |
      | dony@email@.com  | pass4    | Dony  | (777)555-7777    | Invalid email                            |
      | kyle@email.      | pass5    | Kyle  | (666)777-6666    | Invalid email                            |
      | greg.email@com   | pass6    | Greg  | (777)888-7777    | Invalid email                            |
      | @email.com       | pass7    | Otto  | (111)777-6666    | Invalid email                            |
      | karl@.com        | pass8    | Karl  | (111)777-6661    | Invalid email                            |
      |                  | pass9    | Vino  | (777)888-5555    | Email cannot be empty                    |
      | luke@email.com   |          | Luke  | (999)888-5555    | Password cannot be empty                 |
      | owen@email.com   | pass10   |       | (888)888-5555    | Name cannot be empty                     |
      | noah@email.com   | pass11   | Noah  |                  | Emergency contact cannot be empty        |
