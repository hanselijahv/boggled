package BoggledApp;

/**
* BoggledApp/GamePlayerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Thursday, May 9, 2024 12:20:24 PM CST
*/

public final class GamePlayerHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.GamePlayer value = null;

  public GamePlayerHolder ()
  {
  }

  public GamePlayerHolder (BoggledApp.GamePlayer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.GamePlayerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.GamePlayerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.GamePlayerHelper.type ();
  }

}
