package Repository;

import Entity.Account;
import Exceptions.Account_notFound;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private final Map<Long, Account> accounts = new HashMap<>();

    public synchronized void save(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (accounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account already exists");
        }
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(long accountNumber) {
        if (accounts.get(accountNumber) == null) {
            throw new Account_notFound("Account not Found " + accountNumber);
        }
        return accounts.get(accountNumber);
    }

    public Collection<Account> getAccounts() {
        return accounts.values();
    }
    public void update(Account account) {

        if (account == null) {
            throw new IllegalArgumentException(
                    "Account cannot be null"
            );
        }

        long accountNumber = account.getAccountNumber();

        if (!accounts.containsKey(accountNumber)) {
            throw new Account_notFound(
                    "Cannot update. Account not found: " + accountNumber
            );
        }

        accounts.put(accountNumber, account);

        System.out.println("Account updated successfully!");
    }
    public void delete(long accountNumber) {

        if (!accounts.containsKey(accountNumber)) {
            throw new Account_notFound(
                    "Cannot delete. Account not found: " + accountNumber
            );
        }

        accounts.remove(accountNumber);

        System.out.println("Account deleted successfully!");
    }

    public boolean exists(long accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public int count() {
        return accounts.size();
    }
    public void deleteAll() {
        accounts.clear();
    }
}
