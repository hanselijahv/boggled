package BoggledApp;


/**
* BoggledApp/BoggledOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Tuesday, July 2, 2024 11:44:29 PM CST
*/

public interface BoggledOperations 
{

  // User related methods
  void login (String username, String password) throws BoggledApp.UserNotFoundException, BoggledApp.AlreadyLoggedInException;
  void logout (String username) throws BoggledApp.NotLoggedInException;

  // Game related
  void joinWaitingRoom (String playerName);
  boolean isGameReadyToStart () throws BoggledApp.InsufficientPlayerException;
  String getWaitingTime (BoggledApp.Callback objRef);
  String getWaitingRoomInfo (String playerName);
  String getRoundTime (BoggledApp.Callback objRef, String gameID);
  boolean submitWord (String gameID, String playerName, String word, org.omg.CORBA.BooleanHolder isValid, org.omg.CORBA.BooleanHolder canForm, org.omg.CORBA.StringHolder response);
  String getGameID (BoggledApp.Callback objRef, String playerName);
  String getLetters (String gameID);
  boolean isGameFinished (String gameID);
  int currentRound (String gameID);
  int totalRounds (String gameID);

  // score related
  String gameWinner (String gameID);
  int getWinnerScore (String gameID);
  String roundWinner (String gameID) throws BoggledApp.NoWinnerException;
  int roundPoints (String gameID, String playerName);
  int playerPoints (String gameID, String playerName);
  String gameScore (String gameID);
  BoggledApp.Leaderboards getLeaderboard ();
} // interface BoggledOperations
