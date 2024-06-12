package terminalclient;

import BoggledApp.*;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.Scanner;

public class JavaClient {
    static Boggled boggled;

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Boggled";
            boggled = BoggledHelper.narrow(ncRef.resolve_str(name));
            CallbackServant ciaoCallbackImpl = new CallbackServant();
            ciaoCallbackImpl.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(ciaoCallbackImpl);
            Callback cref = CallbackHelper.narrow(ref);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            try {
                boggled.login(username, password);
                System.out.println("Logged in successfully!");
            } catch (UserNotFoundException e) {
                System.out.println("Login failed: " + e.reason);
                return;
            } catch (AlreadyLoggedInException e) {
                System.out.println("Login failed: " + e.reason);
                return;
            }

            System.out.print("Press Enter to Continue: ");
            scanner.nextLine();
            boggled.joinWaitingRoom(username);

            boolean gameStarted = false;
            String gameId = boggled.getGameID(cref, username);
            String previous = "";
            while (true) {
                String waitingRoomPlayers = boggled.getWaitingRoomInfo(username);
                if (!waitingRoomPlayers.equals(previous)) {
                    System.out.println(waitingRoomPlayers);
                    previous = waitingRoomPlayers;
                }

                boolean isReadyToStart = boggled.isGameReadyToStart();
                Thread.sleep(1000);

                String remainingTime = boggled.getWaitingTime(cref);

                if (remainingTime.equals("Room has ended!") && isReadyToStart) {
                    System.out.println("Game is ready to start!");
                    gameStarted = true;
                    boolean scorePrinted = false;
                    while (!boggled.isGameFinished(gameId)) {
                        System.out.println("Current round: " + boggled.currentRound(gameId));
                        String letters = boggled.getLetters(gameId);
                        System.out.println("LETTERS: " + letters);
                        System.out.print("Enter your word: ");
                        String word = scanner.nextLine();
                        System.out.println();
                        BooleanHolder isWordValid = new BooleanHolder();
                        BooleanHolder canForm = new BooleanHolder();
                        StringHolder response = new StringHolder();
                        boggled.submitWord(gameId, username, word, isWordValid, canForm, response);
                        System.out.println(response.value);
                        String roundRemainingTime = boggled.getRoundTime(cref, gameId);
                        System.out.println("Time remaining: " + boggled.getRoundTime(cref, gameId) + " seconds");
                        if (roundRemainingTime.equals("00") && scorePrinted) {
                            System.out.println("Getting ready for the next round...");
                            System.out.println("Wait for 5 secs and press enter!");
                            String enter = scanner.nextLine();
                        } else if (roundRemainingTime.equals("00")) {
                            System.out.println("Your score for this round: " + boggled.roundPoints(gameId, username));
                            System.out.println("Winner for this round: " + boggled.roundWinner(gameId));
                            System.out.println(boggled.gameScore(gameId));
                            scorePrinted = true;
                        }
                    }
                    System.out.println("Game over! The winner is " + boggled.gameWinner(gameId) + " with a score of " + boggled.playerPoints(gameId, username));
                }  else if (remainingTime.equals("Room has ended!") && !gameStarted) { // Check gameStarted before printing the message
                    System.out.println("Not enough players to start the game!");
                    break;
                }
                if (!gameStarted && !remainingTime.equals("Room has ended!")) {
                    System.out.print(remainingTime + " ");
                }
            }
            System.out.println("Game has ended!");
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
