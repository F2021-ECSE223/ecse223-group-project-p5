Feature: Delete Member (p14)
  As a member, I want to delete my member account

  Background: 
    Given the following ClimbSafe system exists: (p14)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |      20 |                  50 |
    Given the following equipment exists in the system: (p14)
      | name           | weight | pricePerWeek |
      | rope           |   3000 |            5 |
      | pickaxe        |   1000 |           50 |
      | portable stove |   2000 |          100 |
    Given the following equipment bundles exist in the system: (p14)
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |
    Given the following members exist in the system: (p14)
      | email         | password  | name  | emergencyContact | nrWeeks | bookableItems       | requestedQuantities | guideRequired | hotelRequired |
      | user1@mail.ca | password1 | User1 | (111)111-1111    |       1 | rope,portable stove |                 3,1 | true          | true          |
      | user2@mail.ca | password2 | User2 | (222)222-2222    |       2 | large bundle        |                   2 | false         | false         |
    Given the following guides exist in the system: (p14)
      | email          | password | name | emergencyContact |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    |
      | john@email.com | pass2    | John | (444)444-4444    |

  Scenario Outline: Delete a member account successfully
    When the member attempts to delete the account with email "<target>" (p14)
    Then the member account with the email "<target>" does not exist (p14)
    Then there are "<numberOfMembers>" members in the system. (p14)

    Examples: 
      | target               | numberOfMembers |
      | user1@mail.ca        |               1 |
      | user2@mail.ca        |               1 |
      | usernotfound@mail.ca |               2 |

  Scenario Outline: Attempting to delete a member that does not exist but guide exists
    When the member attempts to delete the member account with email "<target>" (p14)
    Then the guide account linked to the "<target>" shall exist in the system (p14)
    Then the number of guides in the system is 2 (p14)
    Then the member account with the email "<target>" does not exist (p14)
    Then there are 2 members in the system. (p14)

    Examples: 
      | target         |
      | jeff@email.com |
      | john@email.com |
