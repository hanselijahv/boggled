package BoggledApp;


/**
* BoggledApp/InsufficientPlayersException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Tuesday, 14 May 2024 18:12:23 o'clock CST
*/

public final class InsufficientPlayersException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public InsufficientPlayersException ()
  {
    super(InsufficientPlayersExceptionHelper.id());
  } // ctor

  public InsufficientPlayersException (String _reason)
  {
    super(InsufficientPlayersExceptionHelper.id());
    reason = _reason;
  } // ctor


  public InsufficientPlayersException (String $reason, String _reason)
  {
    super(InsufficientPlayersExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class InsufficientPlayersException
