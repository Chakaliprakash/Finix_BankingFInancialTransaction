package Account_Classes;

import Entity.Account;
import Entity.AccountType;
import Entity.Customer;
import Exceptions.InvalidAmount_Exception;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    private static final BigDecimal MinimumBalance = BigDecimal.valueOf(5000);
    private static final BigDecimal OVER_DRAFT= BigDecimal.valueOf(10000);

    public CurrentAccount(long accountNumber, Customer customer, BigDecimal balance) {
        super(accountNumber, customer, balance);
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmount_Exception("Invalid Balance");
        }
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CURRENT;
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) {
        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0) {
            throw new InvalidAmount_Exception("Amount must be Positive");
        }
        BigDecimal remainingBalance = balance.subtract(amount);
        if(remainingBalance.compareTo(OVER_DRAFT.negate())<=0) {
            throw new InvalidAmount_Exception("OverDraft limit Exceeded");
        }
        balance=remainingBalance;
    }

    @Override
    public BigDecimal calculateInterest() {
        return BigDecimal.ZERO;
    }
}
