package net.team6.boggled.client.gui.input;



import Client_Java.net.team6.boggled.client.gui.clickable.BoggledClickable;
import Client_Java.net.team6.boggled.client.gui.container.BoggledContainer;
import Client_Java.net.team6.boggled.client.gui.container.HorizontalContainer;
import Client_Java.net.team6.boggled.client.gui.container.VerticalContainer;
import Client_Java.net.team6.boggled.client.gui.text.BoggledText;
import Client_Java.net.team6.boggled.client.gui.tools.Spacing;
import Client_Java.net.team6.boggled.client.input.KeyInputConsumer;
import Client_Java.net.team6.boggled.client.state.State;
import Client_Java.net.team6.boggled.common.core.Size;
import Client_Java.net.team6.boggled.common.core.Value;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class BoggledTextInput extends BoggledClickable implements KeyInputConsumer {

    private final Value<String> value;
    private Timer backspaceTimer;
    private boolean capsLock = false;

    private final BoggledContainer container;
    private final BoggledContainer borderContainer;
    private final BoggledContainer contentContainer;

    public BoggledTextInput(String label, Value<String> value) {

        this.value = value;
        margin = new Spacing(0);
        padding = new Spacing(0);

        borderContainer = new VerticalContainer();
        borderContainer.setPadding(new Spacing(2));
        borderContainer.setMargin(new Spacing(0));

        contentContainer = new HorizontalContainer();
        contentContainer.setCenterChildren(true);
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setBackgroundColor(Color.DARK_GRAY);
        contentContainer.setFixedSize(new Size(200, 30));

        borderContainer.addUIComponent(contentContainer);

        container = new VerticalContainer();
        container.setMargin(new Spacing(0));
        container.setPadding(new Spacing(0));

        BoggledText labelText = new BoggledText(label, 16);
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
                    contentContainer.addUIComponent(new BoggledText(currentValue, 16));
                }
            }
        });
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        container.update(state);
        size = container.getSize();

        Color borderColor = Color.GRAY;

        if(state.getKeyInputConsumer() != null && state.getKeyInputConsumer().equals(this)) {
            borderColor = new Color(255,255,143);
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
                contentContainer.addUIComponent(new BoggledText(currentValue, 16));
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
        contentContainer.addUIComponent(new BoggledText(currentValue, 16));
    }

    public void onKeyReleased(int key) {
        if (key == KeyEvent.VK_BACK_SPACE) {
            backspaceTimer.stop();
        }
    }


    @Override
    protected void onFocus(State state) {

    }

    @Override
    public void onClick(State state) {
        state.setKeyInputConsumer(this);
    }

    @Override
    public void onDrag(State state) {

    }

    @Override
    public void onRelease(State state) {

    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }
}
