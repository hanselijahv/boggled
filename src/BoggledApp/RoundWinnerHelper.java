package BoggledApp;


/**
* BoggledApp/RoundWinnerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Thursday, May 9, 2024 12:57:53 PM CST
*/

abstract public class RoundWinnerHelper
{
  private static String  _id = "IDL:BoggledApp/RoundWinner:1.0";

  public static void insert (org.omg.CORBA.Any a, BoggledApp.RoundWinner that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static BoggledApp.RoundWinner extract (org.omg.CORBA.Any a)
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
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "gameID",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "playerID",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "roundScore",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (BoggledApp.RoundWinnerHelper.id (), "RoundWinner", _members0);
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

  public static BoggledApp.RoundWinner read (org.omg.CORBA.portable.InputStream istream)
  {
    BoggledApp.RoundWinner value = new BoggledApp.RoundWinner ();
    value.gameID = istream.read_string ();
    value.playerID = istream.read_string ();
    value.roundScore = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, BoggledApp.RoundWinner value)
  {
    ostream.write_string (value.gameID);
    ostream.write_string (value.playerID);
    ostream.write_long (value.roundScore);
  }

}
