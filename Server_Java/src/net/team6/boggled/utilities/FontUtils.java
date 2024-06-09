package net.team6.boggled.utilities;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FontUtils {
    public static Font loadFont(String path, float size) {
        try {
            InputStream inputStream = FontUtils.class.getResourceAsStream(path);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
            return customFont.deriveFont(Font.BOLD, size);
        } catch (IOException ioe) {
            System.out.println("Font loading failed: " + ioe.getMessage());
        } catch (FontFormatException ffe){
            System.out.println("Font format invalid: " + ffe.getMessage());
        }
        // Return a default font if the custom font fails to load
        return new Font("Arial", Font.PLAIN, 24);
    }
}