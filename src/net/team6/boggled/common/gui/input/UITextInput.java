package net.team6.boggled.common.gui.input;



import net.team6.boggled.client.state.State;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.common.gui.clickable.UIClickable;
import net.team6.boggled.common.gui.container.HorizontalContainer;
import net.team6.boggled.common.gui.container.UIContainer;
import net.team6.boggled.common.gui.container.VerticalContainer;
import net.team6.boggled.common.gui.text.UIText;
import net.team6.boggled.common.gui.tools.Spacing;
import net.team6.boggled.common.input.KeyInputConsumer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class UITextInput extends UIClickable implements KeyInputConsumer {

    private final Value<String> value;

    private UIContainer container;
    private UIContainer borderContainer;
    private UIContainer contentContainer;

    public UITextInput(String label, Value<String> value) {
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
        container.addUIComponent(new UIText(label, 16));
        container.addUIComponent(borderContainer);
    }

    @Override
    public void update(State state) throws SQLException {
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

        if(key == KeyEvent.VK_BACK_SPACE) {
            if(!currentValue.isEmpty()) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
        } else if (key == KeyEvent.VK_SPACE) {
            currentValue += " ";
        } else {
            String keyText = KeyEvent.getKeyText(key);
            if(keyText.length() == 1) {
                currentValue += keyText;
            }
        }

        value.setValue(currentValue);

        contentContainer.clear();
        contentContainer.addUIComponent(new UIText(currentValue, 16));
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
