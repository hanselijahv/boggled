package BoggledApp;


/**
* BoggledApp/NotLoggedInException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Monday, March 10, 2025 8:57:42 PM SGT
*/

public final class NotLoggedInException extends org.omg.CORBA.UserException
{
  public String reason = null;

  public NotLoggedInException ()
  {
    super(NotLoggedInExceptionHelper.id());
  } // ctor

  public NotLoggedInException (String _reason)
  {
    super(NotLoggedInExceptionHelper.id());
    reason = _reason;
  } // ctor


  public NotLoggedInException (String $reason, String _reason)
  {
    super(NotLoggedInExceptionHelper.id() + "  " + $reason);
    reason = _reason;
  } // ctor

} // class NotLoggedInException
