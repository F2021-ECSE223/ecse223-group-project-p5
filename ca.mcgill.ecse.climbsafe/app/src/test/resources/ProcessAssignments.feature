Feature: Process assignments
  As an administrator, I wish to process existing assignments to keep members and the system up to date

  Background: 
    Given the following ClimbSafe system exists:
      | startDate  | nrWeeks | priceOfGuidePerWeek |
      | 2022-01-13 |       5 |                  50 |
    Given the following pieces of equipment exist in the system:
      | name           | weight | pricePerWeek |
      | rope           |   3000 |            5 |
      | pickaxe        |   1000 |           50 |
      | portable stove |   2000 |          100 |
    Given the following equipment bundles exist in the system:
      | name         | discount | items                       | quantity |
      | small bundle |       10 | rope,pickaxe                |      2,1 |
      | large bundle |       25 | rope,pickaxe,portable stove |    2,1,1 |
    Given the following guides exist in the system:
      | email        | password  | name         | emergencyContact |
      | bob@nmc.nt   | passw0rd1 | Bob Smith    |       2005555678 |
      | diana@nmc.nt | diana1991 | Diana Prince |       2005555432 |
    Given the following members exist in the system:
      | email              | password | name             | emergencyContact | nrWeeks | guideRequired | hotelRequired | bookedItems          | bookedItemQuantities |
      | alice@gmail.com    | pass123  | Alice Jones      |       2005551234 |       3 | true          | false         | small bundle,pickaxe |                  2,1 |
      | charlie@hotmail.ca | charlie  | Charles Tremblay |       2005559876 |       3 | false         | true          | large bundle         |                    2 |
      | john@hotmail.ca    | john123  | John Doe         |       2005551234 |       2 | true          | false         | small bundle,rope    |                  2,1 |
      | emily@hotmail.ca   | emily007 | Emily Green      |       2005559876 |       3 | true          | true          | large bundle         |                    2 |
    Given the following assignments exist in the system:
      | memberEmail        | guideEmail   | startWeek | endWeek |
      | alice@gmail.com    | bob@nmc.nt   |         1 |       3 |
      | charlie@hotmail.ca |              |         1 |       3 |
      | john@hotmail.ca    | diana@nmc.nt |         1 |       3 |
      | emily@hotmail.ca   | bob@nmc.nt   |         4 |       5 |

  Scenario Outline: Successfully pay for trip
    When the administrator attempts to confirm payment for "<email>" using authorization code "<code>"
    Then the assignment for "<email>" shall be marked as "Paid"
    Then the assignment for "<email>" shall record the authorization code "<code>"

    Examples: 
      | email              | code         |
      | alice@gmail.com    | CLIMBSAFE200 |
      | charlie@hotmail.ca | CODE321      |

  Scenario Outline: Attempt to pay for trip with an email address that does not exist
    When the administrator attempts to confirm payment for "<email>" using authorization code "<code>"
    Then the member account with the email "<email>" does not exist
    Then there are "4" members in the system
    Then the error "<error>" shall be raised

    Examples: 
      | email            | code         | error                                                     |
      | steve@yahoo.com  | CLIMBSAFE200 | Member with email address steve@yahoo.com does not exist  |
      | dave@hotmail.com | CODE321      | Member with email address dave@hotmail.com does not exist |

  Scenario Outline: Attempt to pay for trip with invalid authorization code
    When the administrator attempts to confirm payment for "<email>" using authorization code "<code>"
    Then the assignment for "<email>" shall be marked as "Assigned"
    Then the error "<error>" shall be raised

    Examples: 
      | email              | code | error                      |
      | alice@gmail.com    |      | Invalid authorization code |
      | charlie@hotmail.ca |      | Invalid authorization code |

  Scenario Outline: Successfully cancel trip for member who has not yet paid
    When the administrator attempts to cancel the trip for "<email>"
    Then the assignment for "<email>" shall be marked as "Cancelled"

    Examples: 
      | email              |
      | alice@gmail.com    |
      | charlie@hotmail.ca |

  Scenario Outline: Successfully cancel trip for member who has already paid
    Given the member with "<email>" has paid for their trip
    When the administrator attempts to cancel the trip for "<email>"
    Then the assignment for "<email>" shall be marked as "Cancelled"
    Then the member with email address "<email>" shall receive a refund of "50" percent

    Examples: 
      | email              |
      | alice@gmail.com    |
      | charlie@hotmail.ca |

  Scenario Outline: Successfully cancel trip for member who has already started their trip
    Given the member with "<email>" has started their trip
    When the administrator attempts to cancel the trip for "<email>"
    Then the assignment for "<email>" shall be marked as "Cancelled"
    Then the member with email address "<email>" shall receive a refund of "10" percent

    Examples: 
      | email              |
      | alice@gmail.com    |
      | charlie@hotmail.ca |

  Scenario Outline: Attempt to cancel trip with an email address that does not exist
    When the administrator attempts to cancel the trip for "<email>"
    Then the member account with the email "<email>" does not exist
    Then there are "4" members in the system
    Then the error "<error>" shall be raised

    Examples: 
      | email            | error                                                     |
      | steve@yahoo.com  | Member with email address steve@yahoo.com does not exist  |
      | dave@hotmail.com | Member with email address dave@hotmail.com does not exist |

  Scenario: Attempt to finish a trip for an assigned assignment
    When the administrator attempts to finish the trip for the member with email "alice@gmail.com"
    Then the error "Cannot finish a trip which has not started" shall be raised
    Then the assignment for "alice@gmail.com" shall be marked as "Assigned"

  Scenario: Attempt to finish a trip for a paid assignment
    Given the member with "charlie@hotmail.ca" has paid for their trip
    When the administrator attempts to finish the trip for the member with email "charlie@hotmail.ca"
    Then the error "Cannot finish a trip which has not started" shall be raised
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Paid"

  Scenario: Attempt to pay for a paid assignment
    Given the member with "charlie@hotmail.ca" has paid for their trip
    When the administrator attempts to confirm payment for "charlie@hotmail.ca" using authorization code "PAYup"
    Then the error "Trip has already been paid for" shall be raised
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Paid"

  Scenario: Attempt to pay for a started assignment
    Given the member with "john@hotmail.ca" has started their trip
    When the administrator attempts to confirm payment for "john@hotmail.ca" using authorization code "PAYup"
    Then the error "Trip has already been paid for" shall be raised
    Then the assignment for "john@hotmail.ca" shall be marked as "Started"

  Scenario: Attempt to pay for a banned member
    Given the member with "emily@hotmail.ca" is banned
    When the administrator attempts to confirm payment for "emily@hotmail.ca" using authorization code "PAYup"
    Then the error "Cannot pay for the trip due to a ban" shall be raised
    Then the member with email "emily@hotmail.ca" shall be "Banned"

  Scenario: Attempt to start a trip for a banned member
    Given the member with "emily@hotmail.ca" is banned
    When the administrator attempts to start the trips for week "4"
    Then the error "Cannot start the trip due to a ban" shall be raised
    Then the member with email "emily@hotmail.ca" shall be "Banned"

  Scenario: Attempt to finish a trip for a banned member
    Given the member with "emily@hotmail.ca" is banned
    When the administrator attempts to finish the trip for the member with email "emily@hotmail.ca"
    Then the error "Cannot finish the trip due to a ban" shall be raised
    Then the member with email "emily@hotmail.ca" shall be "Banned"

  Scenario: Attempt to cancel a trip for a banned member
    Given the member with "emily@hotmail.ca" is banned
    When the administrator attempts to cancel the trip for "emily@hotmail.ca"
    Then the error "Cannot cancel the trip due to a ban" shall be raised
    Then the member with email "emily@hotmail.ca" shall be "Banned"

  Scenario: Attempt to pay for a cancelled assignment
    Given the member with "alice@gmail.com" has cancelled their trip
    When the administrator attempts to confirm payment for "alice@gmail.com" using authorization code "PAYup"
    Then the error "Cannot pay for a trip which has been cancelled" shall be raised
    Then the assignment for "alice@gmail.com" shall be marked as "Cancelled"

  Scenario: Attempt to start a trip for a cancelled assignment
    Given the member with "emily@hotmail.ca" has cancelled their trip
    When the administrator attempts to start the trips for week "4"
    Then the error "Cannot start a trip which has been cancelled" shall be raised
    Then the assignment for "emily@hotmail.ca" shall be marked as "Cancelled"

  Scenario: Attempt to finish a trip for a cancelled assignment
    Given the member with "alice@gmail.com" has cancelled their trip
    When the administrator attempts to finish the trip for the member with email "alice@gmail.com"
    Then the error "Cannot finish a trip which has been cancelled" shall be raised
    Then the assignment for "alice@gmail.com" shall be marked as "Cancelled"

  Scenario: Attempt to pay for a finished assignment
    Given the member with "charlie@hotmail.ca" has finished their trip
    When the administrator attempts to confirm payment for "charlie@hotmail.ca" using authorization code "PAYup"
    Then the error "Cannot pay for a trip which has finished" shall be raised
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Finished"

  Scenario: Attempt to start a trip for a finished assignment
    Given the member with "emily@hotmail.ca" has finished their trip
    When the administrator attempts to start the trips for week "4"
    Then the error "Cannot start a trip which has finished" shall be raised
    Then the assignment for "emily@hotmail.ca" shall be marked as "Finished"

  Scenario: Attempt to cancel a trip for a finished assignment
    Given the member with "charlie@hotmail.ca" has finished their trip
    When the administrator attempts to cancel the trip for "charlie@hotmail.ca"
    Then the error "Cannot cancel a trip which has finished" shall be raised
    Then the assignment for "charlie@hotmail.ca" shall be marked as "Finished"

  Scenario: Administrator starts trips
    Given the member with "alice@gmail.com" has paid for their trip
    When the administrator attempts to start the trips for week "1"
    Then the assignment for "alice@gmail.com" shall be marked as "Started"
    Then the member with email "charlie@hotmail.ca" shall be "Banned"
    Then the member with email "john@hotmail.ca" shall be "Banned"
    Then the assignment for "emily@hotmail.ca" shall be marked as "Assigned"

  Scenario Outline: Successfully finish trip for member
    Given the member with "<email>" has started their trip
    When the administrator attempts to finish the trip for the member with email "<email>"
    Then the assignment for "<email>" shall be marked as "Finished"
    Then the member with email address "<email>" shall receive a refund of "0" percent

    Examples: 
      | email              |
      | alice@gmail.com    |
      | charlie@hotmail.ca |

  Scenario: Finish trip with invalid email
    When the administrator attempts to finish the trip for the member with email "nonexisting@mail.ca"
    Then the member account with the email "nonexisting@mail.ca" does not exist
    Then there are "4" members in the system
    Then the error "Member with email address nonexisting@mail.ca does not exist" shall be raised
