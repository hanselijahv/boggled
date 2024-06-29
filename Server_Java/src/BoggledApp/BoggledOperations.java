package BoggledApp;


/**
* BoggledApp/BoggledOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 1:28:02 PM CST
*/

public interface BoggledOperations 
{
  void login (String username, String password) throws BoggledApp.UserNotFoundException, BoggledApp.AlreadyLoggedInException;
  void joinWaitingRoom (String playerName);
  boolean isGameReadyToStart ();
  String getWaitingTime (BoggledApp.Callback objRef);
  String getWaitingRoomInfo (String playerName);
  String getRoundTime (BoggledApp.Callback objRef, String gameID);
  boolean submitWord (String gameID, String playerName, String word, org.omg.CORBA.BooleanHolder isValid, org.omg.CORBA.BooleanHolder canForm, org.omg.CORBA.StringHolder response);
  String getGameID (BoggledApp.Callback objRef, String playerName);
  String getLetters (String gameID);
  void logout (String username) throws BoggledApp.NotLoggedInException;
  String gameWinner (String gameID);
  String roundWinner (String gameID);
  int roundPoints (String gameID, String playerName);
  int playerPoints (String gameID, String playerName);
  boolean isGameFinished (String gameID);
  String gameScore (String gameID);
  int currentRound (String gameID);
  BoggledApp.Leaderboards getLeaderboard ();
} // interface BoggledOperations
