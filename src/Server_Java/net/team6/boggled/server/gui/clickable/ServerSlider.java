package Server_Java.net.team6.boggled.server.gui.clickable;

import Server_Java.net.team6.boggled.common.core.Size;
import Server_Java.net.team6.boggled.server.gui.tools.Spacing;
import Server_Java.net.team6.boggled.server.state.ServerState;
import Server_Java.net.team6.boggled.utilities.BoggledColors;
import Server_Java.net.team6.boggled.utilities.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ServerSlider extends ServerClickable {

    private double value;
    private final double min;
    private final double max;

    public ServerSlider(double min, double max) {
        this.min = min;
        this.max = max;
        this.value = max;
        this.size = new Size(360, 10);
        this.margin = new Spacing(0,0,15,0);
    }

    @Override
    public void onClick(ServerState state) {
        this.value = getValueAt(state.getInput().getMousePosition().getX());
    }

    @Override
    public void onDrag(ServerState state) {
        this.value = getValueAt(state.getInput().getMousePosition().getX());
    }

    @Override
    public void onRelease(ServerState state) {

    }

    private double getValueAt(double xPosition) {
        double positionOnSlider = xPosition - absolutePosition.getX();
        double percentage = positionOnSlider / size.getWidth();
        double range = max - min;

        double value = min + range * percentage;

        return Math.round(value);
    }

    private int getPixelsOfCurrentValue() {
        double range = max - min;
        double percentage = value - min;

        return (int) ((percentage / range) * size.getWidth());
    }

    @Override
    public Image getSprite() {
        BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = sprite.createGraphics();

        graphics.setColor(BoggledColors.PRIMARY_SHADOW_COLOR);
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        graphics.setColor(BoggledColors.PRIMARY_COLOR);
        graphics.fillRect(0, 0, getPixelsOfCurrentValue(), size.getHeight());

        graphics.dispose();
        return sprite;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    protected void onFocus(ServerState state) {

    }
}
