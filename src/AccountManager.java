import java.io.*;
import java.util.*;

public class AccountManager {
    private Map<String, Account> accounts = new HashMap<>();

    public AccountManager() {
        loadAccounts();
    }

    private void loadAccounts() {
        try (Scanner scanner = new Scanner(new File("accounts.csv"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                accounts.put(data[0], new Account(data[0], data[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Account file not found, starting fresh.");
        }
    }

    public Account createAccount(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();

        String password;
        while (true) {
            System.out.print("Enter password (min 6 characters, must include a special character): ");
            password = scanner.next();
            if (validatePassword(password)) {
                break;
            }
            System.out.println("Invalid password. Must be at least 6 characters long and include at least one special character.");
        }

        Account account = new Account(username, password);
        accounts.put(username, account);
        saveAccounts();
        return account;
    }

    public boolean validatePassword(String password) {
        return password.matches(".*[!@#$%^&*()_+]+.*") && password.length() >= 6;
    }

    private void saveAccounts() {
        try (PrintWriter out = new PrintWriter("accounts.csv")) {
            for (Account account : accounts.values()) {
                out.println(account.getUsername() + "," + account.getPassword());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving accounts.");
        }
    }

    public Account login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        Account account = accounts.get(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        } else {
            System.out.println("Login failed.");
            return null;
        }
    }
}
