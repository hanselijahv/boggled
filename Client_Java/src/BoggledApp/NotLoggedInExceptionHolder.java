package BoggledApp;

/**
* BoggledApp/NotLoggedInExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Wednesday, June 12, 2024 1:58:47 PM SGT
*/

public final class NotLoggedInExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public NotLoggedInException value = null;

  public NotLoggedInExceptionHolder ()
  {
  }

  public NotLoggedInExceptionHolder (NotLoggedInException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = NotLoggedInExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    NotLoggedInExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return NotLoggedInExceptionHelper.type ();
  }

}
