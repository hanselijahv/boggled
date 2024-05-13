package net.team6.boggled.run;

import BoggledApp.Boggled;

import java.util.Scanner;

public class SampleClient {
    public static Connect connect;

    public static Boggled boggledImpl;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boggledImpl = connect.createConnection(args);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        try {
            boggledImpl.login(username, password);
            System.out.println("Login successful!");
            System.out.println("Enter S to Start");
            String start = scanner.nextLine();
            if (start.equals("s")){
                boggledImpl.playGame(username);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
