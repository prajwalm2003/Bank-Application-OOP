import java.util.*;

public abstract class Account {
    protected int accountNumber;
    protected double balance;
    protected List<String> transactions = new ArrayList<>();

    public Account(int accNo) {
        this.accountNumber = accNo;
        this.balance = 0;
        transactions.add("Account created: " + accNo);
    }

    public void deposit(double amt) {
        balance += amt;
        transactions.add("Deposited: " + amt);
    }

    public boolean withdraw(double amt) {
        if (balance >= amt) {
            balance -= amt;
            transactions.add("Withdrawn: " + amt);
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public List<String> getTransactions() {
        return transactions;
    }
}
