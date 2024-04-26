package game;

import javax.swing.*;
import java.awt.*;

public class ImageTextField extends JTextField {
    private ImageIcon imageIcon;

    public ImageTextField(ImageIcon icon) {
        super();
        this.imageIcon = icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imageX = 5;
        int imageY = (getHeight() - imageIcon.getIconHeight()) / 2;
        imageIcon.paintIcon(this, g, imageX, imageY);
    }

    public void setImage(ImageIcon icon) {
        this.imageIcon = icon;
    }
}