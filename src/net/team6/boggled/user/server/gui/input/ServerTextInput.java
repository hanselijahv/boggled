package net.team6.boggled.user.server.gui.input;



import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.user.server.input.ServerKeyInputConsumer;
import net.team6.boggled.user.server.gui.clickable.ServerClickable;
import net.team6.boggled.user.server.gui.container.ServerContainer;
import net.team6.boggled.user.server.gui.container.ServerHorizontalContainer;
import net.team6.boggled.user.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.user.server.gui.text.ServerText;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.state.ServerState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class ServerTextInput extends ServerClickable implements ServerKeyInputConsumer {

    private final Value<String> value;
    private Timer backspaceTimer;
    private boolean capsLock = false;

    private ServerContainer container;
    private ServerContainer borderContainer;
    private ServerContainer contentContainer;

    public ServerTextInput(String label, Value<String> value) {

        this.value = value;
        margin = new Spacing(0);
        padding = new Spacing(0);

        borderContainer = new ServerVerticalContainer();
        borderContainer.setPadding(new Spacing(2));
        borderContainer.setMargin(new Spacing(0));

        contentContainer = new ServerHorizontalContainer();
        contentContainer.setCenterChildren(true);
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setBackgroundColor(Color.DARK_GRAY);
        contentContainer.setFixedSize(new Size(200, 30));

        borderContainer.addUIComponent(contentContainer);

        container = new ServerVerticalContainer();
        container.setMargin(new Spacing(0));
        container.setPadding(new Spacing(0));

        ServerText labelText = new ServerText(label, 16);
        labelText.setPadding(new Spacing(0, 0, 10, 0));
        container.addUIComponent(labelText);

        container.addUIComponent(borderContainer);

        backspaceTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentValue = value.get();
                if (!currentValue.isEmpty()) {
                    currentValue = currentValue.substring(0, currentValue.length() - 1);
                    value.setValue(currentValue);
                    contentContainer.clear();
                    contentContainer.addUIComponent(new ServerText(currentValue, 16));
                }
            }
        });
    }

    @Override
    public void update(ServerState state) throws SQLException {
        super.update(state);
        container.update(state);
        size = container.getSize();

        Color borderColor = Color.GRAY;

        if(state.getKeyInputConsumer() != null && state.getKeyInputConsumer().equals(this)) {
            borderColor = Color.LIGHT_GRAY;
        }

        borderContainer.setBackgroundColor(borderColor);
    }



    @Override
    public void onKeyPressed(int key) {
        String currentValue = value.get();

        if (key == KeyEvent.VK_CAPS_LOCK) {
            capsLock = !capsLock;
        } else if (key == KeyEvent.VK_BACK_SPACE) {
            backspaceTimer.start();
            if(!currentValue.isEmpty()) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
                value.setValue(currentValue);
                contentContainer.clear();
                contentContainer.addUIComponent(new ServerText(currentValue, 16));
            }
        } else if (key == KeyEvent.VK_SPACE) {
            currentValue += " ";
        } else {
            String keyText = KeyEvent.getKeyText(key);
            if(keyText.length() == 1) {
                if (capsLock) {
                    currentValue += keyText.toUpperCase();
                } else {
                    currentValue += keyText.toLowerCase();
                }
            }
        }

        value.setValue(currentValue);

        contentContainer.clear();
        contentContainer.addUIComponent(new ServerText(currentValue, 16));
    }

    @Override
    public void onKeyReleased(int key) {
        if(key == KeyEvent.VK_BACK_SPACE) {
            backspaceTimer.stop();
        }
    }

    @Override
    protected void onFocus(ServerState state) {

    }

    @Override
    public void onClick(ServerState state) {
        state.setKeyInputConsumer(this);
    }

    @Override
    public void onDrag(ServerState state) {

    }

    @Override
    public void onRelease(ServerState state) {

    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    public void clearText() {
        value.setValue("");
        contentContainer.clear();
    }
}
