package BoggledApp;


/**
* BoggledApp/_BoggledStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from boggled.idl
* Thursday, 16 May 2024 15:13:19 o'clock CST
*/

public class _BoggledStub extends org.omg.CORBA.portable.ObjectImpl implements BoggledApp.Boggled
{

  public void login (String username, String password) throws BoggledApp.UserNotFoundException, BoggledApp.AlreadyLoggedInException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("login", true);
                $out.write_string (username);
                $out.write_string (password);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:BoggledApp/UserNotFoundException:1.0"))
                    throw BoggledApp.UserNotFoundExceptionHelper.read ($in);
                else if (_id.equals ("IDL:BoggledApp/AlreadyLoggedInException:1.0"))
                    throw BoggledApp.AlreadyLoggedInExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                login (username, password        );
            } finally {
                _releaseReply ($in);
            }
  } // login

  public void joinWaitingRoom (String playerName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("joinWaitingRoom", true);
                $out.write_string (playerName);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                joinWaitingRoom (playerName        );
            } finally {
                _releaseReply ($in);
            }
  } // joinWaitingRoom

  public String say (BoggledApp.Callback objRef, String message)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("say", true);
                BoggledApp.CallbackHelper.write ($out, objRef);
                $out.write_string (message);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return say (objRef, message        );
            } finally {
                _releaseReply ($in);
            }
  } // say

  public boolean isGameReadyToStart ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("isGameReadyToStart", true);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return isGameReadyToStart (        );
            } finally {
                _releaseReply ($in);
            }
  } // isGameReadyToStart

  public String getWaitingTime (BoggledApp.Callback objRef)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getWaitingTime", true);
                BoggledApp.CallbackHelper.write ($out, objRef);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getWaitingTime (objRef        );
            } finally {
                _releaseReply ($in);
            }
  } // getWaitingTime

  public String getWaitingRoomInfo (String playerName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getWaitingRoomInfo", true);
                $out.write_string (playerName);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getWaitingRoomInfo (playerName        );
            } finally {
                _releaseReply ($in);
            }
  } // getWaitingRoomInfo

  public boolean submitWord (String word, org.omg.CORBA.BooleanHolder isValid, org.omg.CORBA.BooleanHolder canForm, org.omg.CORBA.StringHolder response)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("submitWord", true);
                $out.write_string (word);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                isValid.value = $in.read_boolean ();
                canForm.value = $in.read_boolean ();
                response.value = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return submitWord (word, isValid, canForm, response        );
            } finally {
                _releaseReply ($in);
            }
  } // submitWord

  public String getGameID (BoggledApp.Callback objRef, String playerName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getGameID", true);
                BoggledApp.CallbackHelper.write ($out, objRef);
                $out.write_string (playerName);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getGameID (objRef, playerName        );
            } finally {
                _releaseReply ($in);
            }
  } // getGameID

  public String getLetters (String gameID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getLetters", true);
                $out.write_string (gameID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getLetters (gameID        );
            } finally {
                _releaseReply ($in);
            }
  } // getLetters

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:BoggledApp/Boggled:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     com.sun.corba.se.impl.orbutil.IORCheckImpl.check(str, "BoggledApp._BoggledStub");
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _BoggledStub
