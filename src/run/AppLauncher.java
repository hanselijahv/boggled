package run;

import main.WordGame;
import javax.swing.SwingUtilities;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordGame::new);
    }
}
