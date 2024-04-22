package accessdata;

import references.Game;
import references.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class GameDB {

    public String addGame(Player player, int highestScore) {
        Connection connection = DatabaseConnector.getInstance().getConnection();
        String query = "INSERT INTO game (game_id, player_id, highest_score) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, generateGameID());
            preparedStatement.setString(2, player.getPlayerId());
            preparedStatement.setInt(2, highestScore);
        } catch (Exception e){
            e.getMessage();
            return "Error";
        }
        return "Success";
    }

    public static ArrayList<Game> readGame() {
        try {
            Connection connection = DatabaseConnector.getInstance().getConnection();
            ArrayList<Game> gameArrayList = new ArrayList<>();
            String query = "SELECT * FROM game";
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Game game = new Game(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                gameArrayList.add(game);
            }
            return gameArrayList;
        } catch (SQLException e) {
            System.out.println("[ERROR] Error selecting admin accounts.");
            throw new RuntimeException(e);
        }
    }

    public static String generateGameID() {
        Random random = new Random();
        int ranInt = random.nextInt(9000) + 1000;

        String id = "9" + ranInt;
        if (GameDB.idExists(Integer.parseInt(id))) {
            return generateGameID();
        } else {
            return id;
        }
    }

    public static boolean idExists(int id) {
        Connection connection = DatabaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM game WHERE game_id = ?";

        try {
            PreparedStatement statementGame = connection.prepareStatement(query);
            statementGame.setInt(1, id);
            ResultSet resultSetGame = statementGame.executeQuery();
            if (resultSetGame.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
