package BoggledApp;

/**
* BoggledApp/UserNotFoundExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Saturday, June 29, 2024 7:58:30 PM SGT
*/

public final class UserNotFoundExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public UserNotFoundException value = null;

  public UserNotFoundExceptionHolder ()
  {
  }

  public UserNotFoundExceptionHolder (UserNotFoundException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = UserNotFoundExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    UserNotFoundExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return UserNotFoundExceptionHelper.type ();
  }

}
