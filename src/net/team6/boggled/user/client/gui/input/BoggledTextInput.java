package net.team6.boggled.user.client.gui.input;



import net.team6.boggled.user.client.state.State;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.user.client.gui.clickable.BoggledClickable;
import net.team6.boggled.user.client.gui.container.HorizontalContainer;
import net.team6.boggled.user.client.gui.container.BoggledContainer;
import net.team6.boggled.user.client.gui.container.VerticalContainer;
import net.team6.boggled.user.client.gui.text.BoggledText;
import net.team6.boggled.user.client.gui.tools.Spacing;
import net.team6.boggled.user.client.input.KeyInputConsumer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class BoggledTextInput extends BoggledClickable implements KeyInputConsumer {

    private final Value<String> value;

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

        if (key == KeyEvent.VK_BACK_SPACE) {
            if (!currentValue.isEmpty()) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
        } else if (key == KeyEvent.VK_SPACE) {
            currentValue += " ";
        } else {
            char keyChar = (char) key;
            if (Character.isLetterOrDigit(keyChar) || Character.isWhitespace(keyChar)) {
                currentValue += keyChar;
            }
        }

        value.setValue(currentValue);

        contentContainer.clear();
        contentContainer.addUIComponent(new BoggledText(currentValue, 16));
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
