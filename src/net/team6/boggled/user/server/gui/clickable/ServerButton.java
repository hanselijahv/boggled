package net.team6.boggled.user.server.gui.clickable;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.server.gui.container.ServerContainer;
import net.team6.boggled.user.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.user.server.gui.text.ServerText;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.state.ServerState;
import net.team6.boggled.utilities.BoggledColors;

import java.awt.*;
import java.sql.SQLException;

public class ServerButton extends ServerClickable {

    private ServerContainer container;
    private ServerText label;

    private ServerClickAction serverClickAction;

    protected Color backgroundColor;

    public ServerButton(String label, int fontSize, ServerClickAction serverClickAction) {
        this.label = new ServerText(label, fontSize);
        this.serverClickAction = serverClickAction;
        backgroundColor = Color.GRAY;

        setMargin(new Spacing(5, 0, 0, 0));

        container = new ServerVerticalContainer();
        container.setCenterChildren(true);
        container.addUIComponent(this.label);
        container.setFixedSize(new Size(150, 30));
    }

    @Override
    public void update(ServerState state) throws SQLException {
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
    protected void onFocus(ServerState state) {
    }

    @Override
    public void onClick(ServerState state) throws SQLException {
        serverClickAction.execute(state);
        state.getAudioPlayer().playSound("SFX_UI_MenuSelections.wav");
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


}

