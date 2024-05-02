package test.consoled;


import net.team6.boggled.common.model.Account;
import net.team6.boggled.common.model.GameRoom;

import java.util.ArrayList;

public class GameRoomTester {
	public static void main(String[] args) {

		Account account1 = new Account("1", "PLAYER 1", null);
		Account account2 = new Account("2", "PLAYER 2", null);
		Account account3 = new Account("3", "PLAYER 3", null);
		Account account4 = new Account("4", "PLAYER 4", null);

		GameRoom gameRoom = new GameRoom("1",  new ArrayList<>(), false);
		gameRoom.joinRoom(account1);
		gameRoom.startTimer();

		System.out.println("Waiting for players to join...");

		addPlayerWithDelay(gameRoom, account2, 2);
		addPlayerWithDelay(gameRoom, account3, 4);
		addPlayerWithDelay(gameRoom, account4, 7);

	}

	private static void addPlayerWithDelay(GameRoom gameRoom, Account account, long delay) {
		try {
			Thread.sleep(delay * 1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		gameRoom.joinRoom(account);
	}
}
