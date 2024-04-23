package db;


import references.Settings;
import java.sql.*;


public class SettingsDB {
    //TODO Bryan

    private Connection connection;

    public SettingsDB(){
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    public void updateSettings(Settings settings){ // needs testing
        String query = "UPDATE settings SET waitingTime = ?, roundTime = ?, numberOfRounds = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setTimestamp(1,settings.getWaitingTime());
            statement.setTimestamp(2,settings.getRoundTime());
            statement.setInt(3,settings.getNumberOfRounds());
            statement.execute();
            System.out.println("Saved Settings...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
