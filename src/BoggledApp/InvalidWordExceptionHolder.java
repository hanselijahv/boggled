package BoggledApp;

/**
* BoggledApp/InvalidWordExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Tuesday, 14 May 2024 18:12:23 o'clock CST
*/

public final class InvalidWordExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.InvalidWordException value = null;

  public InvalidWordExceptionHolder ()
  {
  }

  public InvalidWordExceptionHolder (BoggledApp.InvalidWordException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.InvalidWordExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.InvalidWordExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.InvalidWordExceptionHelper.type ();
  }

}
