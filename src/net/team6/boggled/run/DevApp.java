package net.team6.boggled.run;

import net.team6.boggled.server.Server;
import net.team6.boggled.server.ServerRunnable;
import net.team6.boggled.common.db.DatabaseConnector;

import java.sql.SQLException;

public class DevApp {
    public static void main(String[] args) throws SQLException {
        DatabaseConnector.getInstance();
        new Thread(new ServerRunnable(new Server(1280, 720))).start();
    }
}
