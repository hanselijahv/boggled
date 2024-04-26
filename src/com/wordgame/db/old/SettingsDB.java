package com.wordgame.db.old;


import com.wordgame.references.Settings;
import java.sql.*;


public class SettingsDB {
    //TODO Bryan

    private Connection connection;

    public SettingsDB(){
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    public void updateSettings(Settings settings){ // needs testing
        String query = "UPDATE settings SET waiting_time = ?, round_time = ?, number_of_rounds = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,settings.getWaitingTime());
            statement.setInt(2,settings.getRoundTime());
            statement.setInt(3,settings.getNumberOfRounds());
            statement.execute();
            System.out.println("Saved Settings...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
