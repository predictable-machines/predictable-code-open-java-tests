package bank;

import java.time.Instant;

public class Transaction {

    private final TransactionType type;
    private final String fromAccountId;
    private final String toAccountId;
    private final int amount;
    private final Instant timestamp;

    public Transaction(TransactionType type,
                       String fromAccountId,
                       String toAccountId,
                       int amount) {
        this.type = type;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.timestamp = Instant.now();
    }

    public TransactionType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String toString() {
        return type + " " + amount +
               " from=" + fromAccountId +
               " to=" + toAccountId +
               " at=" + timestamp;
    }
}