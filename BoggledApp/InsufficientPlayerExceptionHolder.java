package BoggledApp;

/**
* BoggledApp/InsufficientPlayerExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 7:58:30 PM SGT
*/

public final class InsufficientPlayerExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.InsufficientPlayerException value = null;

  public InsufficientPlayerExceptionHolder ()
  {
  }

  public InsufficientPlayerExceptionHolder (BoggledApp.InsufficientPlayerException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.InsufficientPlayerExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.InsufficientPlayerExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.InsufficientPlayerExceptionHelper.type ();
  }

}
