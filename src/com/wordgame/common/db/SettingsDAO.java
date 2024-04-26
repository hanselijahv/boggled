package com.wordgame.common.db;


import com.wordgame.common.model.Settings;
import java.sql.*;


public class SettingsDAO {
    //TODO Bryan

    private final Connection connection;

    public SettingsDAO(){
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

    public Settings getSettings(){
        String query = "SELECT * FROM settings";
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                if (statement != null) {
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet != null && resultSet.next()){
                        return new Settings(resultSet.getInt("waiting_time"),resultSet.getInt("round_time"),resultSet.getInt("number_of_rounds"));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }


}
