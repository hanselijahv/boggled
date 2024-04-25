package com.wordgame.Server;

import com.wordgame.db.DatabaseConnector;
import com.wordgame.db.PlayerDB;
import com.wordgame.db.SettingsDB;
import com.wordgame.references.Player;
import com.wordgame.references.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    static Scanner scanner = new Scanner(System.in);
    static PlayerDB playerDB = new PlayerDB();
    static SettingsDB settingsDB = new SettingsDB();


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


                playerDB.addPlayer(userName, password);
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
            List<Player> players = playerDB.getAllPlayers();
            System.out.println("Players: ");
            for (Player player : players) {
                System.out.println(player.getPlayerId() + ", " + player.getUsername() + ", " + player.getPassword());
            }
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter player ID to update: ");
            String playerIdToUpdate = scanner.nextLine();


            Player playerToUpdate = null;
            for (Player player : players) {
                if (player.getPlayerId().equalsIgnoreCase(playerIdToUpdate)) {
                    playerToUpdate = player;
                    break;
                }
            }

            if (playerToUpdate == null) {
                System.out.println("No player found with ID: " + playerIdToUpdate);
            } else {
                System.out.print("Enter new username: ");
                String newUsername = scanner.nextLine();
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();

                playerToUpdate.setUsername(newUsername);
                playerToUpdate.setPassword(newPassword);

                playerDB.updatePlayer(playerToUpdate);

                System.out.println("Player updated successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void removeUser() {
        try {
            List<Player> players = playerDB.getAllPlayers();
            System.out.println("Players: ");
            for (Player player : players) {
                System.out.println(player.getPlayerId() + ", " + player.getUsername() + ", " + player.getPassword());
            }
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter player ID to remove: ");
            String playerIdToRemove = scanner.nextLine();

            Player playerToRemove = null;
            for (Player player : players) {
                if (player.getPlayerId().equalsIgnoreCase(playerIdToRemove)) {
                    playerToRemove = player;
                    break;
                }
            }

            if (playerToRemove == null) {
                System.out.println("No player found with ID: " + playerIdToRemove);
            } else {
                playerDB.removePlayer(playerToRemove);
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

            System.out.print("Enter new Number of Rounds: ");
            int numberOfRounds = Integer.parseInt(scanner.nextLine());

            while (numberOfRounds < 3 || numberOfRounds > 10) {
                System.out.println("Number of Rounds must be between 3 and 10.");
                System.out.print("Enter new Number of Rounds: ");
                numberOfRounds = Integer.parseInt(scanner.nextLine());
            }

            Settings settings = new Settings(waitingTime, roundTime, numberOfRounds);
            settingsDB.updateSettings(settings);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
