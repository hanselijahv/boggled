package BoggledApp;

/**
* BoggledApp/NoWinnerExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 10:43:30 PM CST
*/

public final class NoWinnerExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.NoWinnerException value = null;

  public NoWinnerExceptionHolder ()
  {
  }

  public NoWinnerExceptionHolder (BoggledApp.NoWinnerException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.NoWinnerExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.NoWinnerExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.NoWinnerExceptionHelper.type ();
  }

}
