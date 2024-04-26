package com.wordgame.db.old;


import com.wordgame.references.Settings;
import java.sql.*;


public class SettingsDB {
    //TODO Bryan

    private static Connection connection;

    public SettingsDB(){
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    public void updateSettings(Settings settings){ // needs testing
        String query = "UPDATE settings SET waiting_time = ?, round_time = ?, number_of_rounds = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,settings.getWaitingTime());
            statement.setInt(2,settings.getRoundTime());
            statement.setInt(3,settings.getRoundsToWin());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Settings getSettings(){ // needs testing
        String query = "SELECT * FROM settings";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                return new Settings(
                        resultSet.getInt("waiting_time"),
                        resultSet.getInt("round_time"),
                        resultSet.getInt("number_of_rounds"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
