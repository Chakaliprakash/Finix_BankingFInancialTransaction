package Service;

import Account_Classes.CurrentAccount;
import Account_Classes.FixedDepositAccount;
import Account_Classes.SavingAccount;
import Entity.Account;
import Entity.AccountType;
import Entity.Customer;
import Repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

public class BankService {
    private AccountRepository repo;

    public BankService(AccountRepository repo) {
        this.repo = repo;
    }

    public Account createSavingsAccount(long accountNumber, Customer customer, BigDecimal amount) {
        Account account = new SavingAccount(accountNumber, customer, amount);
        repo.save(account);
        return account;
    }

    public Account createCurrentAccount(long accountNumber, Customer customer, BigDecimal amount) {
        Account account = new CurrentAccount(accountNumber, customer, amount);
        repo.save(account);
        return account;
    }

    public Account createFixedAccount(long accountNumber, Customer customer, BigDecimal amount, int duration) {
        Account account = new FixedDepositAccount(accountNumber, customer, amount, duration);
        repo.save(account);
        return account;
    }

    public Account findAccountByAccountNumber(long accountNumber) {
        return repo.getAccount(accountNumber);
    }

    public List<Account> findAllAccounts() {
        return repo.getAccounts().stream().toList();
    }

    public List<Account> findByType(AccountType accountType) {
        return repo.getAccounts().stream()
                .filter(account -> account.getAccountType().equals(accountType))
                .toList();
    }
    public int countAllAccounts() {
        return repo.count();
    }
}
