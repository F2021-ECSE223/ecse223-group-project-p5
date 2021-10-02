Feature: Update Member (p15)
  As a member, I want to update my member account

  Background: 
    Given the following ClimbSafe system exists: (p15)
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |      20 |                  50 |
    Given the following equipment exists in the system: (p15)
      | name           | weight | pricePerWeek |
      | rope           |   3000 |            5 |
      | pickaxe        |   1000 |           50 |
      | portable stove |   2000 |          100 |
    Given the following equipment bundles exist in the system: (p15)
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |
    Given the following members exist in the system: (p15)
      | email         | password  | name  | emergencyContact | nrWeeks | bookableItems       | requestedQuantities | guideRequired | hotelRequired |
      | user1@mail.ca | password1 | User1 | (111)111-1111    |       1 | rope,portable stove |                 3,1 | true          | true          |
      | user2@mail.ca | password2 | User2 | (222)222-2222    |       2 | large bundle        |                   2 | false         | false         |

  Scenario Outline: Update a member account successfully
    When the member with "<email>" attempts to update their account with "<newPassword>", "<newName>", "<newEmergencyContact>", "<newNrWeeks>", "<newBookableItems>", "<newRequestedQuantities>", "<newGuideRequired>", and "<newHotelRequired>" (p15)
    Then the member account with "<email>" shall be updated with "<newPassword>", "<newName>", "<newEmergencyContact>", "<newNrWeeks>", "<newBookableItems>", "<newRequestedQuantities>", "<newGuideRequired>", and "<newHotelRequired>" (p15)
    Then there are 2 members in the system. (p15)

    Examples: 
      | email         | newPassword | newName  | newEmergencyContact | newNrWeeks | newBookableItems | newRequestedQuantities | newGuideRequired | newHotelRequired |
      | user1@mail.ca | newpass1    | NewUser1 | (222)222-222        |          3 | small bundle     |                      2 | false            | false            |
      | user2@mail.ca | newpass2    | NewUser2 | (111)111-111        |          4 | pickaxe,rope     |                    1,2 | true             | true             |

  Scenario Outline: Update member account failed
    When the member with "<email>" attempts to update their account with "<newPassword>", "<newName>", "<newEmergencyContact>", "<newNrWeeks>", "<newBookableItems>", "<newRequestedQuantities>", "<newGuideRequired>", and "<newHotelRequired>" (p15)
    Then the following "<error>" shall be raised. (p15)
    Then there are 2 members in the system. (p15)

    Examples: 
      | email         | newPassword | newName  | newEmergencyContact | newNrWeeks | newBookableItems | newRequestedQuantities | newGuideRequired | newHotelRequired | error                                                                                                                       |
      | user1@mail.ca |             | NewUser1 | (222)222-222        |          3 | small bundle     |                      2 | false            | false            | The password cannot be empty                                                                                                |
      | user1@mail.ca | newpass1    |          | (222)222-222        |          4 | pickaxe          |                      3 | true             | true             | The name cannot be empty                                                                                                    |
      | user1@mail.ca | newpass1    | NewUser1 |                     |          4 | pickaxe          |                      1 | true             | true             | The emergence contact cannot be empty                                                                                       |
      | user1@mail.ca | newpass1    | NewUser1 | (222)222-222        |          0 | pickaxe          |                      2 | true             | true             | The number of weeks must be greater than zero and less than or equal to the number of climbing weeks in the climbing season |
      | user1@mail.ca | newpass1    | NewUser1 | (222)222-222        |         21 | pickaxe          |                      4 | true             | true             | The number of weeks must be greater than zero and less than or equal to the number of climbing weeks in the climbing season |
      | user@mail.ca  | newpass1    | NewUser1 | (222)222-222        |          4 | pickaxe          |                      1 | true             | true             | Member not found                                                                                                            |
      | user1@mail.ca | password1   | User1    | (111)111-1111       |          1 | hammer           |                      3 | true             | false            | Requested item not found                                                                                                    |
