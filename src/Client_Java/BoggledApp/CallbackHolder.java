package Client_Java.BoggledApp;

/**
* BoggledApp/CallbackHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 8, 2024 7:34:01 PM SGT
*/

public final class CallbackHolder implements org.omg.CORBA.portable.Streamable
{
  public Callback value = null;

  public CallbackHolder ()
  {
  }

  public CallbackHolder (Callback initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CallbackHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CallbackHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CallbackHelper.type ();
  }

}
