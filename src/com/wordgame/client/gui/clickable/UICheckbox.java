package com.wordgame.client.gui.clickable;

import com.wordgame.client.core.Size;
import com.wordgame.client.core.Value;
import com.wordgame.utilities.ImageUtils;
import com.wordgame.client.state.State;
import com.wordgame.client.gui.HorizontalContainer;
import com.wordgame.client.gui.Spacing;
import com.wordgame.client.gui.UIComponent;
import com.wordgame.client.gui.UIContainer;
import com.wordgame.client.gui.text.UIText;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UICheckbox extends UIComponent {

    private final UIContainer container;

    public UICheckbox(String label, Value<Boolean> value) {
        this.container = new HorizontalContainer();
        container.addUIComponent(new Checkbox(value));
        container.addUIComponent(new UIText(label, 16));
        container.setPadding(new Spacing(0));
        setMargin(new Spacing(5, 0, 0, 0));
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }


    @Override
    public void update(State state) {
        container.update(state);
        size = container.getSize();
        container.setParent(parent);
        container.setAbsolutePosition(absolutePosition);
    }

    private static class Checkbox extends UIClickable {

        private final Value<Boolean> value;
        private Color color;

        private Checkbox(Value<Boolean> value) {
            this.value = value;
            size = new Size(20, 20);
            color = Color.GRAY;
            margin = new Spacing(0, 15, 0, 0);
        }

        @Override
        public void update(State state) {
            super.update(state);

            color = value.get() ? Color.WHITE : Color.GRAY;

            if(hasFocus) {
                color = Color.LIGHT_GRAY;

                if(isPressed) {
                    color = Color.DARK_GRAY;
                }
            }
        }

        @Override
        protected void onFocus(State state) {}

        @Override
        public void onDrag(State state) {}

        @Override
        public void onRelease(State state) {

        }

        @Override
        public void onClick(State state) {
            if(hasFocus) {
                value.setValue(!value.get());
            }
        }

        @Override
        public Image getSprite() {
            BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
            Graphics2D graphics = sprite.createGraphics();

            graphics.setColor(color);
            if(value.get()) {
                graphics.fillRect(0, 0, size.getWidth(), size.getHeight());
            } else {
                graphics.drawRect(0, 0, size.getWidth() - 1, size.getHeight() - 1);
            }

            graphics.dispose();
            return sprite;
        }
    }
}
