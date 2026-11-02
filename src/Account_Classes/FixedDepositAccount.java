package Account_Classes;

import Entity.Account;
import Entity.AccountType;
import Entity.Customer;
import Exceptions.Insufficientfunds_Exception;
import Exceptions.InvalidAmount_Exception;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

public class FixedDepositAccount extends Account {

    private static final BigDecimal MIN_DEPOSIT = new BigDecimal("5000");
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.07");
    private LocalDate openingDate;
    private LocalDate maturityDate;

    public FixedDepositAccount(long accountNumber, Customer customer, BigDecimal balance, int duration_years) {
        super(accountNumber, customer, balance);
        if (balance.compareTo(MIN_DEPOSIT) < 0) {
            throw new InvalidAmount_Exception("Minimum fixed deposit is ₹5000");
        }
        if (duration_years <= 0) {
            throw new InvalidAmount_Exception("FD duration must be greater than zero");
        }
        this.openingDate = LocalDate.now();
        this.maturityDate = LocalDate.now().plusYears(duration_years);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.FIXED_DEPOSIT;
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new InvalidAmount_Exception("Withdrawal amount must be positive");
        }
        if (LocalDate.now().isBefore(maturityDate)) {
            throw new Insufficientfunds_Exception("Fixed deposit has not matured yet");
        }
        if (amount.compareTo(balance) > 0) {
            throw new Insufficientfunds_Exception("Insufficient FD balance");
        }
        balance = balance.subtract(amount);
    }

    @Override
    public BigDecimal calculateInterest() {
        long years = Period.between(openingDate, maturityDate).getYears();

        return balance.multiply(INTEREST_RATE).multiply(BigDecimal.valueOf(years)).setScale(2, RoundingMode.HALF_UP);
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }
    public boolean isMatured() {
        return !LocalDate.now().isBefore(maturityDate);
    }
}
