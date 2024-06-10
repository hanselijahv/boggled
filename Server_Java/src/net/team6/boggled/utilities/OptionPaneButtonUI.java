package net.team6.boggled.utilities;

import net.team6.boggled.server.audio.ServerAudioPlayer;
import net.team6.boggled.server.dev.settings.ServerSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class OptionPaneButtonUI extends BasicButtonUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));

        Color originalColor = button.getBackground();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(BoggledColors.BUTTON_PRESSED_COLOR);
			ServerSettings gameSettings = null;
			try {
				gameSettings = new ServerSettings(false);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
			ServerAudioPlayer audioPlayer = new ServerAudioPlayer(gameSettings.getAudioSettings());
                audioPlayer.playSound("SFX_UI_MenuSelections.wav");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(originalColor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BoggledColors.BUTTON_HIGHLIGHTED_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }


        });
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JComponent c) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(c.getBackground());
        g.fillRoundRect(0, 0, size.width, size.height,0,0);
    }

}
