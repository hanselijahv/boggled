package BoggledApp;


/**
* BoggledApp/Account.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Monday, May 6, 2024 8:09:33 PM SGT
*/

public final class Account implements org.omg.CORBA.portable.IDLEntity
{
  public String playerID = null;
  public String username = null;
  public String password = null;

  public Account ()
  {
  } // ctor

  public Account (String _playerID, String _username, String _password)
  {
    playerID = _playerID;
    username = _username;
    password = _password;
  } // ctor

} // class Account
