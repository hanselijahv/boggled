package net.team6.boggled.client.gui.component;

import Client_Java.net.team6.boggled.client.gui.tools.Spacing;
import Client_Java.net.team6.boggled.client.state.State;
import Client_Java.net.team6.boggled.common.core.Size;
import Client_Java.net.team6.boggled.utilities.ImageUtils;

import java.awt.*;
import java.sql.SQLException;

public class BoggledHorizontalDivider extends BoggledComponent {

    private Image sprite;

    public BoggledHorizontalDivider() {
        size = new Size(2, 24);
        padding = new Spacing(0);
        margin = new Spacing(16, 0);
        generateSprite();
    }

    private void generateSprite() {
        sprite = ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = (Graphics2D) sprite.getGraphics();

        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        graphics.dispose();
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public void update(State state) throws SQLException {

    }

}