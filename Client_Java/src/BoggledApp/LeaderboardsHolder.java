package BoggledApp;

/**
* BoggledApp/LeaderboardsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 9:33:54 PM CST
*/

public final class LeaderboardsHolder implements org.omg.CORBA.portable.Streamable
{
  public BoggledApp.Leaderboards value = null;

  public LeaderboardsHolder ()
  {
  }

  public LeaderboardsHolder (BoggledApp.Leaderboards initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BoggledApp.LeaderboardsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BoggledApp.LeaderboardsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BoggledApp.LeaderboardsHelper.type ();
  }

}
