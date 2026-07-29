package bank;

/**
 * MainTest.java
 *
 * Walks through the three intentional bugs on this branch:
 * 1. Withdraw allows negative balance
 * 2. Transfer does not conserve money (double fee)
 * 3. Deposit accepts negative values
 *
 * Expected output is included in comments.
 */
public class MainTest {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // --- Create accounts with sufficient balance for all bugs ---
        bank.createAccount("alice", 23); // Alice starts with 23
        bank.createAccount("bob", 50);   // Bob starts with 50

        System.out.println("Initial balances:");
        System.out.println("Alice = " + bank.getAccount("alice").getBalance());
        System.out.println("Bob   = " + bank.getAccount("bob").getBalance());
        System.out.println("---------------------------------");

        // --- Bug 1: Withdraw allows negative balance ---
        System.out.println(">>> Testing Withdraw bug");
        bank.getAccount("alice").withdraw(10); // fee=2 applied internally
        System.out.println("Alice balance after withdraw: " + bank.getAccount("alice").getBalance());
        // Expected: 23 - 10 - 2 = 11

        System.out.println("---------------------------------");

        // --- Bug 2: Transfer does not conserve money ---
        System.out.println(">>> Testing Transfer bug");
        bank.transfer("alice", "bob", 10); // fee=1 applied inside transfer
        System.out.println("Alice balance after transfer: " + bank.getAccount("alice").getBalance());
        System.out.println("Bob balance after transfer: " + bank.getAccount("bob").getBalance());
        // Expected:
        // Alice = 11 - 10 (transfer) - 1 (transfer fee) - 2 (withdraw fee inside transfer) = -2
        // Bob   = 50 + 10 = 60
        // Total money decreased due to double-fee bug

        System.out.println("---------------------------------");

        // --- Bug 3: Deposit allows negative values ---
        System.out.println(">>> Testing Deposit bug");
        bank.deposit("alice", -5); // Deposit negative amount
        System.out.println("Alice balance after negative deposit: " + bank.getAccount("alice").getBalance());
        // Expected:
        // Alice = -2 + (-5) = -7

        System.out.println("---------------------------------");

        // --- Summary ---
        System.out.println("Final balances:");
        System.out.println("Alice = " + bank.getAccount("alice").getBalance()); // -7
        System.out.println("Bob   = " + bank.getAccount("bob").getBalance());   // 60
        System.out.println("---------------------------------");

        System.out.println("Demo complete: all 3 intentional bugs are visible in balances.");
    }
}
