package Client_Java.BoggledApp;

/**
* BoggledApp/BoggledHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 8, 2024 7:34:01 PM SGT
*/

public final class BoggledHolder implements org.omg.CORBA.portable.Streamable
{
  public Boggled value = null;

  public BoggledHolder ()
  {
  }

  public BoggledHolder (Boggled initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledHelper.type ();
  }

}
