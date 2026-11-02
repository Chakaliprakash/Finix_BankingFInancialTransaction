package Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private long transactionId;
    private Account account;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionStatus status;

    public Transaction(long transactionId, Account account, TransactionType transactionType, BigDecimal amount) {
        if (account == null || transactionType == null) {
            throw new IllegalArgumentException(
                    "Account and transaction type cannot be null"
            );
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be positive"
            );
        }
        this.transactionId = transactionId;
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void markAsFailed() {
        this.status = TransactionStatus.FAILED;
    }

    public void markAsSuccess() {
        this.status = TransactionStatus.SUCCESS;
    }

    public void displayTransactionHistory() {

        System.out.println("\n========== TRANSACTION DETAILS ==========");

        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Transaction Type: " + transactionType);
        System.out.println("Amount: " + amount);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Status: " + status);
    }
}
