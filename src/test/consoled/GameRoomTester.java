package test.consoled;


import net.team6.boggled.common.model.GameRoom;
import net.team6.boggled.common.model.Player;

import java.util.ArrayList;

public class GameRoomTester {
	public static void main(String[] args) {

		Player player1 = new Player("1", "PLAYER 1", null);
		Player player2 = new Player("2", "PLAYER 2", null);
		Player player3= new Player("3", "PLAYER 3", null);
		Player player4 = new Player("4", "PLAYER 4", null);

		GameRoom gameRoom = new GameRoom("1",  new ArrayList<>(), false);
		gameRoom.joinRoom(player1);
		gameRoom.startTimer();

		System.out.println("Waiting for players to join...");

		addPlayerWithDelay(gameRoom, player2, 2);
		addPlayerWithDelay(gameRoom, player3, 4);
		addPlayerWithDelay(gameRoom, player4, 7);

	}

	private static void addPlayerWithDelay(GameRoom gameRoom, Player player, long delay) {
		try {
			Thread.sleep(delay * 1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		gameRoom.joinRoom(player);
	}
}
