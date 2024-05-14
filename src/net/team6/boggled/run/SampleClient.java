package net.team6.boggled.run;

import BoggledApp.Boggled;
import BoggledApp.InsufficientPlayersException;

import java.util.Scanner;

public class SampleClient {


    public static void main(String[] args) {
        String gameId = "";
        Scanner scanner = new Scanner(System.in);
        Connect.createConnection(args);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");

        String password = scanner.nextLine();

        try {
            Connect.boggledImpl.login(username, password);
            System.out.println("Login successful!");
            System.out.println("Enter S to Start");
            String start = scanner.nextLine();
            if (start.equals("s")){
                try{
                    gameId = Connect.boggledImpl.playGame(username);
                } catch (InsufficientPlayersException e){
                    System.out.println("Error: " + e.getMessage());
                }
                char[] letters = Connect.boggledImpl.getLetters(gameId);
                System.out.println("Letters: ");
                for (char letter : letters) {
                    System.out.print(letter + " ");
                }
                System.out.println("Enter word: ");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
