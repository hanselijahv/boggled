# Generating IDL Files for Client and Server

This document provides the commands needed to generate Interface Definition Language (IDL) files for both the client and the server in the Boggled game project.

## Prerequisites

Ensure that you have the Java Development Kit (JDK) version 1.8 installed on your system, as the `idlj` command is a part of the JDK.

## Generating IDL Files for the Server

To generate IDL files for the server, navigate to the root directory of your project and run the following command:

```bash
idlj -fall -td "../Server_Java/src" "../CORBA IDL/boggled.idl"
```

This command will generate all files (`-fall`) for the `boggled.idl` file and place them in the `Server_Java/src` directory.

## Generating IDL Files for the Client

To generate IDL files for the client, navigate to the root directory of your project and run the following command:

```bash
idlj -fclient -td "../Client_Java/src" "../CORBA IDL/boggled.idl"
```

This command will generate client-side files (`-fclient`) for the `boggled.idl` file and place them in the `Client_JAVA/src` directory.

After running these commands, you should see the generated Java bindings in the respective `src` directories for the client and the server.