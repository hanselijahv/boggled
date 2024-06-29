package BoggledApp;


/**
* BoggledApp/NoWinnerException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 9:33:59 PM CST
*/

public final class NoWinnerException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public NoWinnerException ()
  {
    super(NoWinnerExceptionHelper.id());
  } // ctor

  public NoWinnerException (String _reason)
  {
    super(NoWinnerExceptionHelper.id());
    reason = _reason;
  } // ctor


  public NoWinnerException (String $reason, String _reason)
  {
    super(NoWinnerExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class NoWinnerException
