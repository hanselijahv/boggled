package BoggledApp;

/**
* BoggledApp/RoundWinnerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Tuesday, 14 May 2024 18:12:23 o'clock CST
*/

public final class RoundWinnerHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.RoundWinner value = null;

  public RoundWinnerHolder ()
  {
  }

  public RoundWinnerHolder (BoggledApp.RoundWinner initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.RoundWinnerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.RoundWinnerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.RoundWinnerHelper.type ();
  }

}
