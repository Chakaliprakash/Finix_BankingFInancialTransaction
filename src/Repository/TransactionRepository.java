package Repository;

import Entity.Transaction;
import Entity.TransactionType;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TransactionRepository {
    private final HashMap<Long, Transaction> transactions = new HashMap<>();

    public synchronized void save(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Transaction cannot be null"
            );
        }
        long transaction_id = transaction.getTransactionId();
        if (transactions.containsKey(transaction_id)) {
            throw new IllegalArgumentException(
                    "Transaction already exists"
            );
        }
        transactions.put(transaction_id, transaction);
    }

    public synchronized Optional<Transaction> findById(long transactionId) {
        return Optional.ofNullable(transactions.get(transactionId));
    }

    public synchronized List<Transaction> findAll() {
        return List.copyOf(transactions.values());
    }

    public synchronized List<Transaction> findByAccountNumber(
            long accountNumber) {

        return transactions.values()
                .stream()
                .filter(transaction ->
                        transaction.getAccount().getAccountNumber() == accountNumber)
                .toList();
    }

    public synchronized List<Transaction> findByType(
            TransactionType transactionType) {

        if (transactionType == null) {
            throw new IllegalArgumentException(
                    "Transaction type cannot be null"
            );
        }

        return transactions.values()
                .stream()
                .filter(transaction ->
                        transaction.getTransactionType() == transactionType)
                .toList();
    }
    public synchronized boolean exists(long transactionId) {
        return transactions.containsKey(transactionId);
    }
    public synchronized int count() {
        return transactions.size();
    }

    public synchronized void delete(long transactionId) {

        if (!transactions.containsKey(transactionId)) {
            throw new NoSuchElementException(
                    "Transaction not found: " + transactionId
            );
        }

        transactions.remove(transactionId);
    }

    public synchronized void deleteAll() {
        transactions.clear();
    }
}
