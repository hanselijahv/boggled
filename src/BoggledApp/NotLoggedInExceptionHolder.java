package BoggledApp;

/**
* BoggledApp/NotLoggedInExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Thursday, May 9, 2024 12:57:53 PM CST
*/

public final class NotLoggedInExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.NotLoggedInException value = null;

  public NotLoggedInExceptionHolder ()
  {
  }

  public NotLoggedInExceptionHolder (BoggledApp.NotLoggedInException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.NotLoggedInExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.NotLoggedInExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.NotLoggedInExceptionHelper.type ();
  }

}
