package BoggledApp;

/**
* BoggledApp/BoggledHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Tuesday, May 14, 2024 3:49:42 PM SGT
*/

public final class BoggledHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.Boggled value = null;

  public BoggledHolder ()
  {
  }

  public BoggledHolder (BoggledApp.Boggled initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.BoggledHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.BoggledHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.BoggledHelper.type ();
  }

}
