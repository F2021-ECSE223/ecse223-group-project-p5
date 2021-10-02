Feature: Delete Guide (p12)
  As an administrator, I wish to delete guide accounts at the end of the season

  Background: 
    Given the following ClimbSafe system exists: (p12)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-03-13 |      10 |                 100 |
    Given the following guides exist in the system: (p12)
      | email          | password | name | emergencyContact |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    |
      | john@email.com | pass2    | John | (444)444-4444    |
    Given the following members exist in the system: (p12)
      | email             | password | name  | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookableItem | quantity |
      | member1@email.com | pass1    | Peter | (666)555-5555    |       1 | true          | true          |              |          |
      | member2@email.com | pass2    | Tyler | (777)444-4444    |       2 | false         | false         |              |          |

  Scenario Outline: Attempting to delete a guide
    When the admin attempts to delete the guide account linked to the "<email>" (p12)
    Then the guide account linked to the "<email>" shall not exist in the system (p12)
    Then the number of guides in the system is "<numberOfGuides>" (p12)

    Examples: 
      | email          | numberOfGuides |
      | jeff@email.com |              1 |
      | john@email.com |              1 |
      | kyle@email.com |              2 |
      | paul@email.com |              2 |

  Scenario Outline: Attempting to delete a guide that does not exist but member exists
    When the admin attempts to delete the guide account linked to the "<email>" (p12)
    Then the member account linked to the "<email>" shall exist in the system (p12)
    Then the number of guides in the system is 2 (p12)

    Examples: 
      | email             |
      | member1@email.com |
      | member2@email.com |
