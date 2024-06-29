package net.team6.boggled.client.gui.text;

import net.team6.boggled.client.gui.component.BoggledComponent;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.state.State;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.utilities.BoggledColors;
import net.team6.boggled.utilities.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SuppressWarnings({"Duplicates"})
public class BoggledText extends BoggledComponent {

    protected String text;
    protected final int fontSize;
    protected final int fontStyle;
    protected final Color color;

    private boolean dropShadow;
    protected int dropShadowOffset;
    private Color shadowColor;

    private Font font;
    private Alignment alignment;

    public BoggledText(String text, int fontSize) {
        this.text = text;
        this.fontSize = fontSize;
        this.fontStyle = Font.BOLD;
        this.color = BoggledColors.PRIMARY_COLOR;
        this.dropShadow = true;
        this.dropShadowOffset = 2;
        this.shadowColor = BoggledColors.PRIMARY_SHADOW_COLOR;

        createFont();
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        if (dropShadow) {
            graphics.setColor(shadowColor);
            graphics.drawString(text, padding.getLeft() + dropShadowOffset, fontSize + padding.getTop() + dropShadowOffset);
        }

        graphics.setColor(color);
        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop());

        graphics.dispose();
        return image;
    }

    @Override
    public void update(State state) {
        calculateSize();
    }

    private void calculateSize() {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(font);
        int width = fontMetrics.stringWidth(text) + padding.getHorizontal();
        int height = fontMetrics.getHeight() + padding.getVertical();

        if(dropShadow) {
            width += dropShadowOffset;
        }

        size = new Size(width, height);
    }

    private void createFont() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("font/MP16REG.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
            font = customFont.deriveFont(fontStyle, fontSize);
        } catch (IOException ioe) {
            System.out.println("Font loading failed: " + ioe.getMessage());
        } catch (FontFormatException ffe){
            System.out.println("Font format invalid: " + ffe.getMessage());
        }
    }

    public void setText(String text) {
        this.text = text;
    }

}
