package BoggledApp;

/**
* BoggledApp/CallbackHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Tuesday, July 2, 2024 11:44:27 PM CST
*/

public final class CallbackHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.Callback value = null;

  public CallbackHolder ()
  {
  }

  public CallbackHolder (BoggledApp.Callback initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.CallbackHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.CallbackHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.CallbackHelper.type ();
  }

}
