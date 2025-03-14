package net.team6.boggled.utilities;

import net.team6.boggled.client.audio.AudioPlayer;
import net.team6.boggled.client.game.settings.GameSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyledButtonUI extends BasicButtonUI {

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
                GameSettings gameSettings = new GameSettings(false);
                AudioPlayer audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
                audioPlayer.playSound("SFX_UI_MenuSelections.wav");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(originalColor);
            }


        });
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (c instanceof JToggleButton) {
            yOffset += 2;
        }

        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }

}