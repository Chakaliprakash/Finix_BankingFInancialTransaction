# Finix: Banking & Financial Transaction

A Java banking domain (savings, current and fixed deposit accounts, deposits, withdrawals, transfers and a transaction repository) with a web dashboard.

## Web dashboard

The `web/` folder holds a standalone front end with no build step. Your accounts appear as 3D cards that tilt with the pointer, come forward when selected, and flip to show account details.

To run it, open `web/index.html` in a browser, or serve the folder:

```bash
cd web
python3 -m http.server 8000
# then visit http://localhost:8000
```

What you can do:

- Deposit, withdraw and transfer between accounts, with live checks before you confirm
- Open a Savings, Current or Fixed deposit account, or close one
- Browse activity by type and by account

The dashboard follows the same rules as the Java classes: Savings keep at least ₹1,000, Current accounts allow a ₹10,000 overdraft, and Fixed deposits start at ₹5,000 and only allow withdrawals after maturity. Data is stored in your browser (localStorage). Use "Reset demo data" in the sidebar to return to the starting accounts.

## Java backend

Source lives in `src/`. Run `Main.java` for a console walkthrough of account creation, deposits, withdrawals, transfers and the transaction repository.
