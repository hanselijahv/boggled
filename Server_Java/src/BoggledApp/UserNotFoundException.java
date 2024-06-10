package BoggledApp;


/**
* BoggledApp/UserNotFoundException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../CORBA IDL/boggled.idl
* Monday, June 10, 2024 12:30:57 AM CST
*/

public final class UserNotFoundException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public UserNotFoundException ()
  {
    super(UserNotFoundExceptionHelper.id());
  } // ctor

  public UserNotFoundException (String _reason)
  {
    super(UserNotFoundExceptionHelper.id());
    reason = _reason;
  } // ctor


  public UserNotFoundException (String $reason, String _reason)
  {
    super(UserNotFoundExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class UserNotFoundException
