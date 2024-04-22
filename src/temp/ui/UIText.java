package temp.ui;

import temp.core.Size;
import temp.gfx.ImageUtils;
import temp.state.State;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SuppressWarnings({"SpellCheckingInspection", "Duplicates"})
public class UIText extends UIComponent {

    protected String text;
    protected final int fontSize;
    protected final int fontStyle;
    protected final String fontFamily;
    protected final Color color;

    private final boolean dropShadow;
    private final int dropShadowOffset;
    private final Color shadowColor;

    private Font font;

    public UIText(String text, int fontSize) {
        this.text = text;
        this.fontSize = fontSize;
        this.fontStyle = Font.BOLD;
        this.color = Color.WHITE;
        this.fontFamily = "Times New Roman";
        this.dropShadow = true;
        this.dropShadowOffset = 2;
        this.shadowColor = new Color(140,140, 140);

        createFont();
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        if(dropShadow) {
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
        //createFont();
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
            InputStream inputStream = UIText.class.getResourceAsStream("/font/MP16REG.ttf");
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
