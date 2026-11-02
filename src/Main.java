import Entity.*;
import Repository.AccountRepository;
import Repository.TransactionRepository;
import Service.BankService;
import Service.TransactionService;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        AccountRepository repo = new AccountRepository();

        BankService bankService = new BankService(repo);

        TransactionService transactionService =
                new TransactionService(bankService);

        Customer customer1 = new Customer(
                101, "Prakash", "Hyderabad", "6302714896"
        );

        Customer customer2 = new Customer(
                102, "Rahul", "Bangalore", "63027148967"
        );

        Customer customer3 = new Customer(
                103, "Kiran", "Chennai", "630271489656"
        );

        System.out.println("\n========== ACCOUNT CREATION ==========");


        Account savings = bankService.createSavingsAccount(
                1001, customer1, new BigDecimal("10000")
        );

        Account current = bankService.createCurrentAccount(
                1002, customer2, new BigDecimal("20000")
        );

        Account fixed = bankService.createFixedAccount(
                1003, customer3, new BigDecimal("50000"), 12
        );

        System.out.println("Savings Account Created: " + savings);
        System.out.println("Current Account Created: " + current);
        System.out.println("Fixed Account Created: " + fixed);


        // 4. Find Account By Account Number
        System.out.println("\n========== FIND ACCOUNT ==========");

        Account account = bankService.findAccountByAccountNumber(1001);

        System.out.println(account);


        // 5. Display All Accounts
        System.out.println("\n========== ALL ACCOUNTS ==========");

        bankService.findAllAccounts().forEach(System.out::println);


        // 6. Filter Accounts By Type
        System.out.println("\n========== SAVINGS ACCOUNTS ==========");

        bankService.findByType(AccountType.SAVINGS)
                .forEach(System.out::println);


        // 7. Count All Accounts
        System.out.println("\n========== ACCOUNT COUNT ==========");

        System.out.println(
                "Total Accounts: " + bankService.countAllAccounts()
        );


        // 8. Deposit
        System.out.println("\n========== DEPOSIT ==========");

        transactionService.deposit(
                1001, new BigDecimal("5000")
        );

        System.out.println(
                "After Deposit: " +
                        bankService.findAccountByAccountNumber(1001)
        );


        // 9. Withdraw
        System.out.println("\n========== WITHDRAW ==========");

        transactionService.withdraw(
                1001, new BigDecimal("2000")
        );

        System.out.println(
                "After Withdrawal: " +
                        bankService.findAccountByAccountNumber(1001)
        );


        // 10. Transfer
        System.out.println("\n========== FUND TRANSFER ==========");

        transactionService.transfer(
                1001,
                1002,
                new BigDecimal("3000")
        );

        System.out.println("Sender After Transfer:");

        System.out.println(
                bankService.findAccountByAccountNumber(1001)
        );

        System.out.println("Receiver After Transfer:");

        System.out.println(
                bankService.findAccountByAccountNumber(1002)
        );


        // 11. Update Account
        System.out.println("\n========== UPDATE ACCOUNT ==========");

        Account updateAccount =
                bankService.findAccountByAccountNumber(1001);

        repo.update(updateAccount);


        // 12. Check Account Exists
        System.out.println("\n========== EXISTS CHECK ==========");

        System.out.println("Account 1001 Exists: " + repo.exists(1001));

        System.out.println("Account 9999 Exists: " + repo.exists(9999));



        System.out.println("\n========== DELETE ACCOUNT ==========");

        repo.delete(1003);

        System.out.println(
                "Remaining Accounts: " + bankService.countAllAccounts()
        );


        // 14. Exception Handling
        System.out.println("\n========== EXCEPTION TESTING ==========");

        try {

            bankService.findAccountByAccountNumber(9999);

        } catch (Exception e) {

            System.out.println("Exception: " + e.getMessage());
        }


        // 15. Final Account Details
        System.out.println("\n========== FINAL ACCOUNT DETAILS ==========");

        bankService.findAllAccounts().forEach(System.out::println);


        // 16. Delete All Accounts (Optional)
        // repo.deleteAll();
        transactionService.deposit(1001, new BigDecimal("50000"));
        transactionService.getBalance(1001,AccountType.SAVINGS);

        TransactionRepository transactionRepo =
                new TransactionRepository();

        Transaction transaction = new Transaction(
                10001,
                savings,
                TransactionType.DEPOSIT,
                new BigDecimal("5000")
        );

        transaction.markAsSuccess();

// Save
        transactionRepo.save(transaction);

// Find by ID
        transactionRepo.findById(10001)
                .ifPresent(Transaction::displayTransactionHistory);

// Find by account
        transactionRepo.findByAccountNumber(1001)
                .forEach(Transaction::displayTransactionHistory);

// Find by type
        transactionRepo.findByType(TransactionType.DEPOSIT)
                .forEach(Transaction::displayTransactionHistory);

// Count
        System.out.println("Total Transactions: " + transactionRepo.count());

// Exists
        System.out.println(transactionRepo.exists(10001));
        System.out.println("\n========== APPLICATION COMPLETED ==========");
    }
}