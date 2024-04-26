package com.wordgame.run;

import com.wordgame.server.Server;
import com.wordgame.server.ServerRunnable;
import com.wordgame.common.db.DatabaseConnector;

import java.sql.SQLException;

public class DevApp {
    public static void main(String[] args) throws SQLException {
        DatabaseConnector.getInstance();
        new Thread(new ServerRunnable(new Server(1280, 720))).start();
    }
}
