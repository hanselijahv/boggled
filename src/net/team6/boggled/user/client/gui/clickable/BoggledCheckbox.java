package net.team6.boggled.user.client.gui.clickable;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.utilities.ImageUtils;
import net.team6.boggled.user.client.state.State;
import net.team6.boggled.user.client.gui.container.HorizontalContainer;
import net.team6.boggled.user.client.gui.tools.Spacing;
import net.team6.boggled.user.client.gui.component.BoggledComponent;
import net.team6.boggled.user.client.gui.container.BoggledContainer;
import net.team6.boggled.user.client.gui.text.BoggledText;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class BoggledCheckbox extends BoggledComponent {

    private final BoggledContainer container;

    public BoggledCheckbox(String label, Value<Boolean> value) {
        this.container = new HorizontalContainer();
        container.addUIComponent(new Checkbox(value));
        container.addUIComponent(new BoggledText(label, 16));
        container.setPadding(new Spacing(0));
        setMargin(new Spacing(5, 0, 0, 0));
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }


    @Override
    public void update(State state) throws SQLException {
        container.update(state);
        size = container.getSize();
        container.setParent(parent);
        container.setAbsolutePosition(absolutePosition);
    }

    private static class Checkbox extends BoggledClickable {

        private final Value<Boolean> value;
        private Color color;

        private Checkbox(Value<Boolean> value) {
            this.value = value;
            size = new Size(20, 20);
            color = Color.GRAY;
            margin = new Spacing(0, 15, 0, 0);
        }

        @Override
        public void update(State state) throws SQLException {
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
                state.getAudioPlayer().playSound("SFX_UI_MenuSelections.wav");
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
