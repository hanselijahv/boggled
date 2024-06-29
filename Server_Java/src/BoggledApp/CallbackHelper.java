package BoggledApp;


/**
* BoggledApp/CallbackHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 10:43:28 PM CST
*/

abstract public class CallbackHelper
{
  private static String  _id = "IDL:BoggledApp/Callback:1.0";

  public static void insert (org.omg.CORBA.Any a, BoggledApp.Callback that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static BoggledApp.Callback extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (BoggledApp.CallbackHelper.id (), "Callback");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static BoggledApp.Callback read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_CallbackStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, BoggledApp.Callback value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static BoggledApp.Callback narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof BoggledApp.Callback)
      return (BoggledApp.Callback)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      BoggledApp._CallbackStub stub = new BoggledApp._CallbackStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static BoggledApp.Callback unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof BoggledApp.Callback)
      return (BoggledApp.Callback)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      BoggledApp._CallbackStub stub = new BoggledApp._CallbackStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
