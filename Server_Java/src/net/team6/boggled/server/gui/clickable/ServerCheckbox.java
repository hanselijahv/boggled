package net.team6.boggled.server.gui.clickable;

import Server_Java.net.team6.boggled.common.core.Size;
import Server_Java.net.team6.boggled.common.core.Value;
import Server_Java.net.team6.boggled.server.gui.component.ServerComponent;
import Server_Java.net.team6.boggled.server.gui.container.ServerContainer;
import Server_Java.net.team6.boggled.server.gui.container.ServerHorizontalContainer;
import Server_Java.net.team6.boggled.server.gui.text.ServerText;
import Server_Java.net.team6.boggled.server.gui.tools.Spacing;
import Server_Java.net.team6.boggled.server.state.ServerState;
import Server_Java.net.team6.boggled.utilities.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class ServerCheckbox extends ServerComponent {

    private final ServerContainer container;

    public ServerCheckbox(String label, Value<Boolean> value) {
        this.container = new ServerHorizontalContainer();
        container.addUIComponent(new Checkbox(value));
        container.addUIComponent(new ServerText(label, 16));
        container.setPadding(new Spacing(0));
        setMargin(new Spacing(5, 0, 0, 0));
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }


    @Override
    public void update(ServerState state) throws SQLException {
        container.update(state);
        size = container.getSize();
        container.setParent(parent);
        container.setAbsolutePosition(absolutePosition);
    }

    private static class Checkbox extends ServerClickable {

        private final Value<Boolean> value;
        private Color color;

        private Checkbox(Value<Boolean> value) {
            this.value = value;
            size = new Size(20, 20);
            color = Color.GRAY;
            margin = new Spacing(0, 15, 0, 0);
        }

        @Override
        public void update(ServerState state) throws SQLException {
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
        protected void onFocus(ServerState state) {}

        @Override
        public void onDrag(ServerState state) {}

        @Override
        public void onRelease(ServerState state) {

        }

        @Override
        public void onClick(ServerState state) {
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
