package BoggledApp;


/**
* BoggledApp/SettingsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Monday, May 6, 2024 8:09:33 PM SGT
*/

abstract public class SettingsHelper
{
  private static String  _id = "IDL:BoggledApp/Settings:1.0";

  public static void insert (org.omg.CORBA.Any a, BoggledApp.Settings that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static BoggledApp.Settings extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[0] = new org.omg.CORBA.StructMember (
            "waitingTime",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[1] = new org.omg.CORBA.StructMember (
            "roundTime",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "roundsToWin",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (BoggledApp.SettingsHelper.id (), "Settings", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static BoggledApp.Settings read (org.omg.CORBA.portable.InputStream istream)
  {
    BoggledApp.Settings value = new BoggledApp.Settings ();
    value.waitingTime = istream.read_long ();
    value.roundTime = istream.read_long ();
    value.roundsToWin = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, BoggledApp.Settings value)
  {
    ostream.write_long (value.waitingTime);
    ostream.write_long (value.roundTime);
    ostream.write_long (value.roundsToWin);
  }

}
