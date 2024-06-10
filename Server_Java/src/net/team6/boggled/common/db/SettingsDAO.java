package net.team6.boggled.common.db;

import net.team6.boggled.common.model.Settings;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SettingsDAO implements DAO<Settings> {

    private final Connection connection;
    public static SettingsDAO settingsDAOImpl = new SettingsDAO();

    public SettingsDAO(){
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    @Override
    public List<Settings> getAll() throws SQLException {
        List<Settings> settingsList = new ArrayList<>();
        try (
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM settings")) {
            while (resultSet.next()) {
                int waitingTime = resultSet.getInt("waiting_time");
                int roundTime = resultSet.getInt("round_time");
                int numberOfRounds = resultSet.getInt("number_of_rounds");

                Settings settings = new Settings(waitingTime, roundTime, numberOfRounds);
                settingsList.add(settings);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return settingsList;
    }

    @Override
    public boolean insert(Settings settings) throws SQLException {
        return false;   // TODO: Implement
    }

    // TODO: Resolve missing reference for setting
    @Override
    public boolean update(Settings settings, String[] params) throws SQLException {
        String query = "UPDATE settings SET waiting_time = ?, round_time = ?, number_of_rounds = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,settings.getWaitingTime());
            statement.setInt(2,settings.getRoundTime());
            statement.setInt(3,settings.getRoundsToWin());
            return statement.execute();
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to update settings: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Settings settings) throws SQLException {
        return false;   // TODO: Implement
    }
}
