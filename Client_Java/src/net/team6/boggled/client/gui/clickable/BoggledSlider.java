package net.team6.boggled.client.gui.clickable;

import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.client.state.State;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.utilities.BoggledColors;
import net.team6.boggled.utilities.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BoggledSlider extends BoggledClickable {

    private double value;
    private final double min;
    private final double max;

    public BoggledSlider(double min, double max) {
        this.min = min;
        this.max = max;
        this.value = max;
        this.size = new Size(360, 10);
        this.margin = new Spacing(0,0,15,0);
    }

    @Override
    public void onClick(State state) {
        this.value = getValueAt(state.getInput().getMousePosition().getX());
    }

    @Override
    public void onDrag(State state) {
        this.value = getValueAt(state.getInput().getMousePosition().getX());
    }

    @Override
    public void onRelease(State state) {

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
    protected void onFocus(State state) {

    }
}
