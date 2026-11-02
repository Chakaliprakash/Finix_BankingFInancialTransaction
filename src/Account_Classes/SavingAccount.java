package Account_Classes;

import Entity.Account;
import Entity.AccountType;
import Entity.Customer;
import Exceptions.InvalidAmount_Exception;

import java.math.BigDecimal;

public class SavingAccount extends Account {
    private static final BigDecimal MIN_BALANCE = new BigDecimal("1000");
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.04");

    public SavingAccount(long accountNumber, Customer customer, BigDecimal balance) {
        super(accountNumber, customer, balance);
        if(balance.compareTo(MIN_BALANCE) < 0) {
            throw new InvalidAmount_Exception("Minimum balance is " + MIN_BALANCE);
        }
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS;
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) {
        if(amount==null || amount.compareTo(MIN_BALANCE) < 0) {
            throw new InvalidAmount_Exception("Withdrawal amount must be positive");
        }
        BigDecimal remainingBalance = balance.subtract(amount);
        if(remainingBalance.compareTo(MIN_BALANCE) < 0) {
            throw new InvalidAmount_Exception("Minimum balance must be maintained");
        }
        balance=remainingBalance;
    }

    @Override
    public BigDecimal calculateInterest() {
        return balance.multiply(INTEREST_RATE);
    }

}
