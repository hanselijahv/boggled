package net.team6.boggled.client.gui.clickable;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.gui.text.BoggledText;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.utilities.BoggledColors;

import java.awt.*;
import java.sql.SQLException;

public class BoggledButton extends BoggledClickable {

    private BoggledContainer container;
    private BoggledText label;

    private ClickAction clickAction;

    protected Color backgroundColor;

    public BoggledButton(String label, int fontSize, ClickAction clickAction) {
        this.label = new BoggledText(label, fontSize);
        this.clickAction = clickAction;
        backgroundColor = Color.GRAY;

        setMargin(new Spacing(5, 0, 0, 0));

        container = new VerticalContainer();
        container.setCenterChildren(true);
        container.addUIComponent(this.label);
        container.setFixedSize(new Size(150, 30));
    }

    public BoggledButton(String label, int fontSize) {
        this.label = new BoggledText(label, fontSize);
        backgroundColor = Color.GRAY;

        setMargin(new Spacing(5, 0, 0, 0));

        container = new VerticalContainer();
        container.setCenterChildren(true);
        container.addUIComponent(this.label);
        container.setFixedSize(new Size(150, 30));
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        container.update(state);
        size = container.getSize();

        Color color = BoggledColors.BUTTON_COLOR;

        if(hasFocus) {
            color = BoggledColors.BUTTON_HIGHLIGHTED_COLOR;
        }

        if(isPressed) {
            color = BoggledColors.BUTTON_PRESSED_COLOR;
        }

        container.setBackgroundColor(color);
    }

    @Override
    protected void onFocus(State state) {
    }

    @Override
    public void onClick(State state) throws SQLException {
        clickAction.execute(state);
        //state.getAudioPlayer().playSound("SFX_UI_MenuSelections.wav");
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

