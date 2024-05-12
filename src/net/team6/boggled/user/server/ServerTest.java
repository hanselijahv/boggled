package net.team6.boggled.user.server;

import net.team6.boggled.common.db.DatabaseConnector;
import net.team6.boggled.common.db.AccountDAO;
import net.team6.boggled.common.exceptions.UserNotFoundException;
import net.team6.boggled.common.model.Account;
import net.team6.boggled.common.model.Settings;

import java.util.List;
import java.util.Scanner;

public class ServerTest {

    static Scanner scanner = new Scanner(System.in);
    public static AccountDAO accountDAOImpl = new AccountDAO();
    //static SettingsDB settingsDB = new SettingsDB();


    public static void main(String[] args) {
        try {
            // TO DO: console testing, remove later
            DatabaseConnector.getInstance();
            home();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void home() {
        try {
            while (true) {
                System.out.println("ADMIN - Home");
                System.out.println("---------------");
                System.out.println("\t1] EDIT PLAYERS");
                System.out.println("\t2] EDIT SETTINGS");
                System.out.println("\t3] SHUT DOWN GAME");
                System.out.print(": ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        editUserMenu();
                        break;
                    case 2:
                        changeSettings();
                        break;
                    case 3:
                        System.out.println("Shutting down game...");
                        System.exit(0);
                }

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void editUserMenu() {
        try {
            System.out.println("ADMIN - Edit Users");
            System.out.println("---------------");
            System.out.println("\t1] ADD PLAYERS");
            System.out.println("\t2] UPDATE PLAYERS");
            System.out.println("\t3] REMOVE PLAYERS");
            System.out.println("\t4] Back");
            System.out.print(": ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    updateUser();
                    break;
                case 3:
                    removeUser();
                    break;
                case 4:
                    home();
                    break;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void addUser() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter user name: ");
                String userName = scanner.nextLine();
                System.out.print("Enter user password: ");
                String password = scanner.nextLine();

                // Validation
                if (userName.isEmpty() || password.isEmpty()) {
                    System.out.println("Username or password cannot be empty. Please try again.");
                    continue; // Skip this iteration and go to the next loop iteration
                }


                Account account = new Account(null, userName, password);
                accountDAOImpl.insert(account);
                System.out.println("Added successfully!");

                System.out.println();
                System.out.print("Add another [y/n]? ");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("n")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void updateUser() {
        try {
            List<Account> accounts = accountDAOImpl.getAll();
            System.out.println("Players: ");
            for (Account account : accounts) {
                System.out.println(account.getPlayerId() + ", " + account.getUsername() + ", " + account.getPassword());
            }
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter player ID to update: ");
            String playerIdToUpdate = scanner.nextLine();


            Account accountToUpdate = null;
            for (Account account : accounts) {
                if (account.getPlayerId().equalsIgnoreCase(playerIdToUpdate)) {
                    accountToUpdate = account;
                    break;
                }
            }

            if (accountToUpdate == null) {
                System.out.println("No player found with ID: " + playerIdToUpdate);
            } else {
                System.out.print("Enter new username: ");
                String newUsername = scanner.nextLine();
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();

                String[] params = {newUsername, newPassword};

                accountDAOImpl.update(accountToUpdate, params);

                System.out.println("Player updated successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void removeUser() {
        try {
            List<Account> accounts = accountDAOImpl.getAll();
            System.out.println("Players: ");
            for (Account account : accounts) {
                System.out.println(account.getPlayerId() + ", " + account.getUsername() + ", " + account.getPassword());
            }
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter player ID to remove: ");
            String playerIdToRemove = scanner.nextLine();

            Account accountToRemove = null;
            for (Account account : accounts) {
                if (account.getPlayerId().equalsIgnoreCase(playerIdToRemove)) {
                    accountToRemove = account;
                    break;
                }
            }

            if (accountToRemove == null) {
                System.out.println("No player found with ID: " + playerIdToRemove);
            } else {
                accountDAOImpl.delete(accountToRemove);
                System.out.println("Player removed successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void changeSettings() {
        try {
            System.out.println("ADMIN - Edit Settings");
            System.out.println("---------------");
            System.out.print("Enter new Waiting Time (in seconds): ");
            int waitingTime = Integer.parseInt(scanner.nextLine());

            while (waitingTime < 10 || waitingTime > 30) {
                System.out.println("Waiting Time must be between 10 and 30 seconds.");
                System.out.print("Enter new Waiting Time (in seconds): ");
                waitingTime = Integer.parseInt(scanner.nextLine());
            }

            System.out.print("Enter new Round Time (in seconds): ");
            int roundTime = Integer.parseInt(scanner.nextLine());

            while (roundTime < 30 || roundTime > 120) {
                System.out.println("Round Time must be between 30 and 120 seconds.");
                System.out.print("Enter new Round Time (in seconds): ");
                roundTime = Integer.parseInt(scanner.nextLine());
            }

            System.out.print("Enter new Number of Rounds to Win: ");
            int numRoundsToWin = Integer.parseInt(scanner.nextLine());

            while (numRoundsToWin < 3 || numRoundsToWin > 10) {
                System.out.println("Number of Rounds must be between 3 and 10.");
                System.out.print("Enter new Number of Rounds to Win: ");
                numRoundsToWin = Integer.parseInt(scanner.nextLine());
            }

            Settings settings = new Settings(waitingTime, roundTime, numRoundsToWin);
            //settingsDB.updateSettings(settings);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void login(String username, String password) throws UserNotFoundException {
        boolean isAuthenticated = accountDAOImpl.authenticatePlayer(username, password);
        //System.out.println(username + " " + password);
        if (!isAuthenticated) {
            throw new UserNotFoundException("User '" + username + "' not found or invalid credentials.");
        }
        System.out.println("User '" + username + " logged in successfully.");
    }

}
