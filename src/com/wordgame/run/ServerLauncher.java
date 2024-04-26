package com.wordgame.run;

import com.wordgame.boggled.server.Server;
import com.wordgame.boggled.server.ServerRunnable;
import com.wordgame.db.old.DatabaseConnector;

public class ServerLauncher {
    public static void main(String[] args) {
        DatabaseConnector.getInstance();
        new Thread(new ServerRunnable(new Server(1280, 720))).start();
    }
}
