package net.team6.boggled.run;

import net.team6.boggled.user.server.dev.Server;
import net.team6.boggled.common.db.DatabaseConnector;

import java.applet.Applet;
import java.sql.SQLException;

public class ServerLauncher extends Applet {
    private static final Server server;

    static {
        try {
            server = new Server(1280, 720);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        DatabaseConnector.getInstance();
        server.start();
    }
}
