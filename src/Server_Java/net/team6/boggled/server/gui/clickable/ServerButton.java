package Server_Java.net.team6.boggled.server.gui.clickable;

import Server_Java.net.team6.boggled.common.core.Size;
import Server_Java.net.team6.boggled.server.gui.container.ServerContainer;
import Server_Java.net.team6.boggled.server.gui.container.ServerVerticalContainer;
import Server_Java.net.team6.boggled.server.gui.text.ServerText;
import Server_Java.net.team6.boggled.server.gui.tools.Spacing;
import Server_Java.net.team6.boggled.server.state.ServerState;
import Server_Java.net.team6.boggled.utilities.BoggledColors;

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
       // state.getAudioPlayer().playSound("SFX_UI_MenuSelections.wav");
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

