package Entity;

import java.math.BigDecimal;

public abstract class Account {
    private final long accountNumber;
    private final Customer customer;

    protected BigDecimal balance;

    public Account(long accountNumber, Customer customer, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public abstract AccountType getAccountType() ;
    public synchronized void deposit(BigDecimal amount) {
        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be positive"
            );
        }
        balance = balance.add(amount);
    }
    public abstract void withdraw(BigDecimal amount);
    public abstract BigDecimal calculateInterest();

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", customer=" + customer +
                ", balance=" + balance +
                '}';
    }
}
