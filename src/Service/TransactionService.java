package Service;

import Entity.Account;
import Entity.AccountType;
import Exceptions.InvalidAmount_Exception;

import java.math.BigDecimal;

public class TransactionService {
    private final BankService bankService;

    public TransactionService(BankService bankService) {
        this.bankService = bankService;
    }

    public void deposit(long accountNumber, BigDecimal amount) {
        Account account = bankService.findAccountByAccountNumber(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(long accountNumber, BigDecimal amount) {
        Account account = bankService.findAccountByAccountNumber(accountNumber);
        account.withdraw(amount);
    }
    public synchronized void transfer(
            long fromAccountNumber,
            long toAccountNumber,
            BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new InvalidAmount_Exception(
                    "Transfer amount must be positive"
            );
        }
        if (fromAccountNumber == toAccountNumber) {
            throw new IllegalArgumentException(
                    "Cannot transfer to the same account"
            );
        }
        Account sender = bankService.findAccountByAccountNumber(fromAccountNumber);
        Account receiver = bankService.findAccountByAccountNumber(toAccountNumber);

        sender.withdraw(amount);
        receiver.deposit(amount);
    }
    public void getBalance(long accountNumber,AccountType  accountType) {
        Account account = bankService.findAccountByAccountNumber(accountNumber);
        if(!account.getAccountType().equals(accountType)) {
            throw new IllegalArgumentException("Type of account does not match");
        }
        System.out.println("Account Balance for "+accountNumber+" : "+account.getBalance());
    }
}
