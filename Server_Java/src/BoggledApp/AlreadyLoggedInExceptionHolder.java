package BoggledApp;

/**
* BoggledApp/AlreadyLoggedInExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Tuesday, July 2, 2024 11:44:29 PM CST
*/

public final class AlreadyLoggedInExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.AlreadyLoggedInException value = null;

  public AlreadyLoggedInExceptionHolder ()
  {
  }

  public AlreadyLoggedInExceptionHolder (BoggledApp.AlreadyLoggedInException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.AlreadyLoggedInExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.AlreadyLoggedInExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.AlreadyLoggedInExceptionHelper.type ();
  }

}
