import sys

from omniORB import CORBA

import BoggledApp

host = "localhost"
port = "12345"
orb = CORBA.ORB_init(["-ORBInitRef", f"NameService=corbaname::${host}:${port}"], CORBA.ORB_ID)

# orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)

# error on line 11, dependency issues yata
obj = orb.resolve_initial_references("NameService")
rootContext = obj._narrow(CORBA.NamingContext)

if rootContext is None:
    print("Failed to narrow the root naming context")
    sys.exit(1)

name = [CORBA.NameComponent("test", "my_context"), CORBA.NameComponent("Boggled", "Object")]
try:
    obj = rootContext.resolve(name)
except CORBA.NamingContext.NotFound:
    print("Name not found")
    sys.exit(1)

boggledObj = obj._narrow(BoggledApp.Boggled)

if boggledObj is None:
    print("Object reference is not an BoggledApp::Boggled")
    sys.exit(1)

try:
    result = boggledObj.getGameID("playerName")
    print(result)
except Exception as e:
    print("An exception occurred while calling the method: ", e)
