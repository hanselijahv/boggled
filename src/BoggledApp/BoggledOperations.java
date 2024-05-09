package BoggledApp;


/**
* BoggledApp/BoggledOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Thursday, May 9, 2024 12:57:53 PM CST
*/

public interface BoggledOperations 
{

  // authentication logic
  void login (String username, String password) throws BoggledApp.UserNotFoundException;
  void logout (String username) throws BoggledApp.NotLoggedInException;

  // game logic
  String playGame (String username) throws BoggledApp.InsufficientPlayersException;
  void sendWord (String gameID, String word) throws BoggledApp.InvalidWordException;
  char[] getLetters (String gameID);
  boolean isGameOver (String gameID);
  boolean isRoundOver (String gameID);
  BoggledApp.RoundWinner getRoundWinner (String gameID);
  BoggledApp.GameWinner getGameWinner (String gameID);

  // setting logic
  int getRoundTime (String gameID);
  int getWaitingTime (String gameID);
  int getNumRounds (String gameID);

  // UI
  BoggledApp.GameWinner[] getLeaderboards ();
  BoggledApp.GamePlayer[] getGamePlayers (String gameID);
} // interface BoggledOperations
