Feature: Update Guide (p11)
  As a guide, I wish to update my personal information linked to my account

  Background: 
    Given the following ClimbSafe system exists: (p11)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-03-13 |      10 |                 100 |
    Given the following guides exist in the system: (p11)
      | email          | password | name | emergencyContact |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    |
      | john@email.com | pass2    | John | (444)444-4444    |

  Scenario Outline: A guide updates their info successfully
    When the guide with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newEmergencyContact>" (p11)
    Then their guide account information will be updated and is now "<email>", "<newPassword>", "<newName>", and "<newEmergencyContact>" (p11)
    Then the number of guides in the system is 2 (p11)

    Examples: 
      | email          | newPassword | newName | newEmergencyContact |
      | jeff@email.com | pass5       | Jake    | (111)111-1111       |
      | john@email.com | pass6       | Johnny  | (111)777-7777       |

  Scenario Outline: A guide updates their info unsuccessfully
    When the guide with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newEmergencyContact>" (p11)
    Then the following "<error>" shall be raised (p11)
    Then the guide account information will not be updated and will keep "<email>", "<password>", "<name>", and "<emergencyContact>" (p11)
    Then the number of guides in the system is 2 (p11)

    Examples: 
      | email          | password | name | emergencyContact | newPassword | newName | newEmergencyContact | error                             |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    |             | Jeff    | (555)666-5555       | Password cannot be empty          |
      | john@email.com | pass2    | John | (444)444-4444    | pass2       |         | (444)444-7777       | Name cannot be empty              |
      | john@email.com | pass2    | John | (444)444-4444    | pass2       | John    |                     | Emergency contact cannot be empty |
