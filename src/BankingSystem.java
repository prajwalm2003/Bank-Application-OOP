import java.util.*;

public class BankingSystem {
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, User> users = new HashMap<>();
    static int accountCounter = 1001;

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            if (choice == 1) register();
            else if (choice == 2) login();
            else break;
        }
    }

    static void register() {
        System.out.print("Enter username: ");
        String u = sc.next();
        System.out.print("Enter password: ");
        String p = sc.next();

        users.put(u, new User(u, p));
        System.out.println("Registration successful!");
    }

    static void login() {
        System.out.print("Username: ");
        String u = sc.next();
        System.out.print("Password: ");
        String p = sc.next();

        User user = users.get(u);

        if (user != null && user.login(u, p)) {
            System.out.println("Login successful!");
            userMenu(user);
        } else {
            System.out.println("Invalid credentials");
        }
    }

    static void userMenu(User user) {
        if (user.getAccount() == null) {
            System.out.println("Create account: 1.Savings  2.Current");
            int type = sc.nextInt();

            if (type == 1)
                user.setAccount(new SavingsAccount(accountCounter++));
            else
                user.setAccount(new CurrentAccount(accountCounter++));

            System.out.println("Account created: " + user.getAccount().getAccountNumber());
        }

        while (true) {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Balance");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");

            int ch = sc.nextInt();
            Account acc = user.getAccount();

            if (ch == 1) {
                System.out.print("Amount: ");
                acc.deposit(sc.nextDouble());
            }
            else if (ch == 2) {
                System.out.print("Amount: ");
                if (!acc.withdraw(sc.nextDouble()))
                    System.out.println("Insufficient balance");
            }
            else if (ch == 3) {
                System.out.println("Balance: " + acc.getBalance());
            }
            else if (ch == 4) {
                System.out.print("Enter target username: ");
                String target = sc.next();
                User targetUser = users.get(target);

                if (targetUser != null && targetUser.getAccount() != null) {
                    System.out.print("Amount: ");
                    transfer(acc, targetUser.getAccount(), sc.nextDouble());
                } else {
                    System.out.println("User not found or no account");
                }
            }
            else if (ch == 5) {
                for (String t : acc.getTransactions())
                    System.out.println(t);
            }
            else break;
        }
    }

    static void transfer(Account from, Account to, double amt) {
        if (from.withdraw(amt)) {
            to.deposit(amt);
            from.transactions.add("Transferred " + amt + " to " + to.getAccountNumber());
            to.transactions.add("Received " + amt + " from " + from.getAccountNumber());
            System.out.println("Transfer successful");
        } else {
            System.out.println("Insufficient balance");
        }
    }
}
