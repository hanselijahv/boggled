//
// Created by Benny on 5/23/24.
//

#include "boggled.hh"
#include <iostream>

int main(int argc, char **argv) {
    try {
        // Initialize the ORB
        CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);

        // Get the root POA
        CORBA::Object_var obj = orb->resolve_initial_references("RootPOA");
        PortableServer::POA_var poa = PortableServer::POA::_narrow(obj);

        // Convert the stringified object reference into an object
        // TODO: CHANGE HOST ADDRESS
        obj = orb->string_to_object("corbaloc::localhost:12345/BoggledApp");

        // Narrow the object to the correct type
        BoggledApp::Boggled_var boggled = BoggledApp::Boggled::_narrow(obj);

        if (CORBA::is_nil(boggled)) {
            std::cerr << "Can't narrow reference to type BoggledApp::Boggled" << std::endl;
            return 1;
        }

        // Invoke operations on the object
        // TODO: Replace username and password with user input
        boggled->login("ben", "gil");

        // Destroy the ORB
        orb->destroy();

    } catch (CORBA::SystemException &ex) {
        std::cerr << "Caught CORBA::" << ex._name() << std::endl;
        return 1;
    } catch (CORBA::Exception &ex) {
        std::cerr << "Caught CORBA::Exception: " << ex._name() << std::endl;
        return 1;
    } catch (omniORB::fatalException &fe) {
        std::cerr << "Caught omniORB::fatalException:" << std::endl;
        std::cerr << "  file: " << fe.file() << std::endl;
        std::cerr << "  line: " << fe.line() << std::endl;
        std::cerr << "  mesg: " << fe.errmsg() << std::endl;
        return 1;
    }
    return 0;
}