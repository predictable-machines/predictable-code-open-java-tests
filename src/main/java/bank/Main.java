package bank;

public class Main {

    public static void main(String[] args) {
        // Creates empty bank 
        Bank bank = new Bank();

        // Creates two accounts with id and initial balance
        bank.createAccount("alice", 1000);
        bank.createAccount("bob", 500);

        // Creates an account from user-supplied input (name and initial balance),
        // used here WITHOUT validation
        String userName = args[0];
        int userBalance = Integer.parseInt(args[1]);
        bank.createAccount(userName, userBalance);

        // Executes some operations
        bank.deposit("alice", 200);
        bank.withdraw("bob", 100);
        bank.transfer("alice", "bob", 300);

        // Prints final state
        bank.printSummary();
    }
}
