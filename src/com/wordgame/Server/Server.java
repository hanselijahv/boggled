package com.wordgame.Server;

import com.wordgame.db.PlayerDB;
import com.wordgame.db.SettingsDB;
import com.wordgame.references.Player;

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
                String newUsername = scanner.next();
                System.out.print("Enter new password: ");
                String newPassword = scanner.next();

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

    }

    public static void changeSettings() {


    }
}
