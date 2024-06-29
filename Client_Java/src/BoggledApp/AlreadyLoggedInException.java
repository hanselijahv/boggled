package BoggledApp;


/**
* BoggledApp/AlreadyLoggedInException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 9:33:54 PM CST
*/

public final class AlreadyLoggedInException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public AlreadyLoggedInException ()
  {
    super(AlreadyLoggedInExceptionHelper.id());
  } // ctor

  public AlreadyLoggedInException (String _reason)
  {
    super(AlreadyLoggedInExceptionHelper.id());
    reason = _reason;
  } // ctor


  public AlreadyLoggedInException (String $reason, String _reason)
  {
    super(AlreadyLoggedInExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class AlreadyLoggedInException
