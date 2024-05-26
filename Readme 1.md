# ATM Cash Withdrawal System

## Objective

To implement a "Cash Withdrawal" function for an ATM that dispenses bank-notes based on a user-specified amount. The implementation should:

- Use the minimum number of bank-notes while dispensing the amount.
- Maintain the availability of various denominations in the ATM machine.
- Be flexible to handle any bank denomination as long as it is a multiple of 100.
- Support parallel cash withdrawals, allowing two or more customers to withdraw money simultaneously.
- Handle exceptional situations appropriately.
- Include unit test cases.

## Problem Statement

Develop a cash withdrawal function for an ATM. Ensure that the solution:

1. Uses the minimum number of bank-notes for dispensing the specified amount.
2. Maintains the availability of various denominations within the ATM.
3. Is flexible to support any denomination as long as it is a multiple of 100.
4. Supports concurrent cash withdrawals.
5. Handles exceptional situations effectively.

## Non-Functional Requirements (NFRs)

- **Readme.md**: The duration of this exercise is 90 minutes. Manage your time accordingly.
- Clearly state any assumptions made.
- Do not seek help online or through other sources.

## Evaluation Criteria

1. **Code Completeness/Correctness**: The solution should be complete and correct.
2. **Code Structure**: Ensure modularity, use of OOP principles and design patterns, appropriate size of classes and functions, and low n-path complexity.
3. **Code Quality**: Follow proper naming conventions, logical package/class structure, and include relevant log messages (avoid unnecessary comments).
4. **Choice of Data Structures**: Use appropriate data structures for efficiency.
5. **Efficiency of Code**: Leverage multi-threading where applicable.
6. **Code Test Cases**: Follow TDD principles if possible and provide sufficient test coverage.

## Solution
1. As we run the main application, the ATM machine gets initialized with pre-defined denominations.
2. Main application acts as client, where we withdraw amount from the ATM Machine.
3. After successful withdrawal, we return back the denominations used to dispense the withdrawal amount.
4. We have Junit tests to test against different possible test cases.

## Assumptions
1. We have assumed the denominations are always in multiples of 100.
2. We have assumed three different denominations available which are 500,200,100.

## Testing the Application
1. Application is tested using JDK 22
2. IDE used to test application is IntelliJ

## How to run the application
1. Clone the repository
2. Run the Main.java file directly from IntelliJ
3. To validate against the test cases run the ATMMachineTest.java file
