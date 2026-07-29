package bank;

public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank();

        bank.createAccount("alice", 1000);
        bank.createAccount("bob", 500);

        bank.deposit("alice", 200);
        bank.withdraw("bob", 100);
        bank.transfer("alice", "bob", 300);

        bank.printSummary();
    }
}
