package BoggledApp;


/**
* BoggledApp/InsufficientPlayerException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 7:58:30 PM SGT
*/

public final class InsufficientPlayerException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public InsufficientPlayerException ()
  {
    super(InsufficientPlayerExceptionHelper.id());
  } // ctor

  public InsufficientPlayerException (String _reason)
  {
    super(InsufficientPlayerExceptionHelper.id());
    reason = _reason;
  } // ctor


  public InsufficientPlayerException (String $reason, String _reason)
  {
    super(InsufficientPlayerExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class InsufficientPlayerException
