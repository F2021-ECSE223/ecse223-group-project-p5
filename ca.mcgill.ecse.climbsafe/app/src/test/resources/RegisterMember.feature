Feature: Register Member (p9)
  As a prospective member, I want to register a member account in the system

  Background: 
    Given the following ClimbSafe system exists: (p9)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |      20 |                  50 |
    Given the following equipment exists in the system: (p9)
      | name           | weight | pricePerWeek |
      | rope           |   3000 |            5 |
      | pickaxe        |   1000 |           50 |
      | portable stove |   2000 |          100 |
    Given the following equipment bundles exist in the system: (p9)
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |
    Given the following members exist in the system: (p9)
      | email         | password  | name  | emergencyContact | nrWeeks | bookableItems       | requestedQuantities | guideRequired | hotelRequired |
      | user1@mail.ca | password1 | User1 | (111)111-1111    |       1 | rope,portable stove |                 3,1 | true          | true          |
      | user2@mail.ca | password2 | User2 | (222)222-2222    |       2 | large bundle        |                   2 | false         | false         |
    Given the following guides exist in the system: (p9)
      | email          | password | name | emergencyContact |
      | jeff@email.com | pass1    | Jeff | (555)555-5555    |
      | john@email.com | pass2    | John | (444)444-4444    |

  Scenario Outline: Register a member account successfully
    When a new member attempts to register with "<email>" , "<password>" , "<name>", "<emergencyContact>", "<nrWeeks>", "<bookableItems>", "<requestedQuantities>", "<guideRequired>", and "<hotelRequired>" (p9)
    Then a new member account shall exist with "<email>", "<password>", "<name>", "<emergencyContact>", "<nrWeeks>", "<bookableItems>", "<requestedQuantities>", "<guideRequired>", and "<hotelRequired>" (p9)
    Then there are 3 members in the system. (p9)

    Examples: 
      | email         | password  | name  | emergencyContact | nrWeeks | bookableItems               | requestedQuantities | guideRequired | hotelRequired |
      | user3@mail.ca | password3 | User3 | (333)333-3333    |       3 | rope,pickaxe                |                 4,2 | true          | false         |
      | user4@mail.ca | password4 | User4 | (444)444-4444    |       4 | large bundle,portable stove |                 1,2 | false         | true          |

  Scenario Outline: Register member account failed
    When a new member attempts to register with "<email>" , "<password>" , "<name>", "<emergencyContact>", "<nrWeeks>", "<bookableItems>", "<requestedQuantities>", "<guideRequired>", and "<hotelRequired>" (p9)
    Then the following "<error>" shall be raised. (p9)
    Then there are 2 members in the system. (p9)
    Then there is no member account for "<email>" (p9)

    Examples: 
      | email          | password  | name  | emergencyContact | nrWeeks | bookableItems  | requestedQuantities | guideRequired | hotelRequired | error                                                                                                                       |
      | user1@mail.ca  | password1 | User1 | (111)111-1111    |       1 | rope           |                   3 | true          | false         | A member with this email already exists                                                                                     |
      | user@ mail.ca  | password  | User  | (111)222-333     |       1 | pickaxe        |                   2 | true          | false         | The email must not contain any spaces                                                                                       |
      | user@mail@ca   | password  | User  | (111)222-333     |       1 | portable stove |                   3 | true          | false         | Invalid email                                                                                                               |
      | user@.ca       | password  | User  | (111)222-333     |       1 | portable stove |                   3 | true          | false         | Invalid email                                                                                                               |
      | user@mail      | password  | User  | (111)222-333     |       1 | rope           |                   3 | true          | false         | Invalid email                                                                                                               |
      | user.mail@ca   | password  | User  | (111)222-333     |       1 | rope           |                   3 | true          | false         | Invalid email                                                                                                               |
      | @mail.ca       | password  | User  | (111)222-333     |       1 | rope           |                   3 | true          | false         | Invalid email                                                                                                               |
      | user@mail.ca   |           | User  | (555)555-5555    |       5 | rope           |                   3 | true          | true          | The password cannot be empty                                                                                                |
      | user@mail.ca   | password  |       | (555)555-5555    |       5 | rope           |                   3 | true          | true          | The name cannot be empty                                                                                                    |
      | user@mail.ca   | password  | User  |                  |       5 | rope           |                   3 | false         | true          | The emergency contact cannot be empty                                                                                       |
      | user@mail.ca   | password  | User  | (555)555-5555    |       0 | rope           |                   3 | false         | true          | The number of weeks must be greater than zero and less than or equal to the number of climbing weeks in the climbing season |
      | user@mail.ca   | password  | User  | (555)555-5555    |      21 | rope           |                   3 | true          | false         | The number of weeks must be greater than zero and less than or equal to the number of climbing weeks in the climbing season |
      | admin@nmc.nt   | password  | User  | (555)555-5555    |       5 | rope           |                   3 | true          | false         | The email entered is not allowed for members                                                                                |
      | john@email.com | password1 | User1 | (111)111-1111    |       1 | rope           |                   3 | true          | false         | A guide with this email already exists                                                                                      |
      | user@mail.ca   | password1 | User1 | (111)111-1111    |       1 | hammer         |                   3 | true          | false         | Requested item not found                                                                                                    |
