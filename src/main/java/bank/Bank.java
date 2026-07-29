package bank;

import java.util.*;

public class Bank {

    private final Map<String, Account> accounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public void createAccount(String id, int initialBalance) {
        if (accounts.containsKey(id)) {
            throw new IllegalArgumentException("Account already exists");
        }
        accounts.put(id, new Account(id, initialBalance));
    }

    public void deposit(String accountId, int amount) {
        Account account = getAccount(accountId);
        account.deposit(amount);
        transactions.add(new Transaction(
                TransactionType.DEPOSIT,
                null,
                accountId,
                amount
        ));
    }

    public void withdraw(String accountId, int amount) {
        Account account = getAccount(accountId);
        account.withdraw(amount);
        transactions.add(new Transaction(
                TransactionType.WITHDRAWAL,
                accountId,
                null,
                amount
        ));
    }

    public void transfer(String fromId, String toId, int amount) {
        Account from = getAccount(fromId);
        Account to = getAccount(toId);

        int fee = 1;

        from.withdraw(amount + fee);
        to.deposit(amount);

        transactions.add(new Transaction(
                TransactionType.TRANSFER,
                fromId,
                toId,
                amount
        ));
    }


    public void quickDeposit(String accountId, int amount) {
        Account account = accounts.get(accountId);
        account.deposit(amount);
    }

    public Account getAccount(String id) {
        Account account = accounts.get(id);
        if (account == null) {
            throw new IllegalArgumentException("Unknown account: " + id);
        }
        return account;
    }

    public void printSummary() {
        System.out.println("Accounts:");
        for (Account a : accounts.values()) {
            System.out.println(a.getId() + " balance=" + a.getBalance());
        }

        System.out.println("\nTransactions:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}
