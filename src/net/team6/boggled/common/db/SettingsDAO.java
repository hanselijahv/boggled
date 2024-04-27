package net.team6.boggled.common.db;


import net.team6.boggled.common.model.Settings;
import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class SettingsDAO implements DAO<Settings> {

    private final Connection connection;

    public SettingsDAO(){
        this.connection = DatabaseConnector.getInstance().getConnection();
    }

    @Override
    public List<Settings> getAll() throws SQLException {
        String query = "SELECT * FROM settings";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return Collections.singletonList(new Settings(
                        resultSet.getInt("waiting_time"),
                        resultSet.getInt("round_time"),
                        resultSet.getInt("number_of_rounds")
                ));
            }
        } catch (SQLException e) {
            throw new SQLException("[ERROR] Failed to get settings: " + e.getMessage());
        }

        return Collections.emptyList(); // TODO: Implement
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
