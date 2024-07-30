import Entity.User;
import service.AccountService;
import service.UserService;

import java.util.Scanner;

public class BankingApplication {
    private static UserService userService = new UserService();
    private static AccountService accountService = new AccountService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to Simple Banking System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void register() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        if (userService.registerUser(user)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Email may already be in use.");
        }
    }

    private static void login() {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        User user = userService.loginUser(email, password);
        if (user != null) {
            System.out.println("Login successful!");
            accountMenu(user.getId());
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static void accountMenu(int userId) {
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    if (accountService.createAccount(userId)) {
                        System.out.println("Account created successfully!");
                    } else {
                        System.out.println("Account creation failed.");
                    }
                    break;
                case 2:
                    System.out.println("Enter account ID:");
                    int accountId = scanner.nextInt();
                    System.out.println("Enter amount to deposit:");
                    double amount = scanner.nextDouble();
                    if (accountService.deposit(accountId, amount)) {
                        System.out.println("Deposit successful!");
                    } else {
                        System.out.println("Deposit failed.");
                    }
                    break;
                case 3:
                    System.out.println("Enter account ID:");
                    accountId = scanner.nextInt();
                    System.out.println("Enter amount to withdraw:");
                    amount = scanner.nextDouble();
                    if (accountService.withdraw(accountId, amount)) {
                        System.out.println("Withdrawal successful!");
                    } else {
                        System.out.println("Withdrawal failed. Check balance or try again.");
                    }
                    break;
                case 4:
                    System.out.println("Enter account ID:");
                    accountId = scanner.nextInt();
                    double balance = accountService.checkBalance(accountId);
                    System.out.println("Your balance is: " + balance);
                    break;
                case 5:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
