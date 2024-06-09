package net.team6.boggled.client.state.waiting.elements;

import Client_Java.net.team6.boggled.client.gui.container.HorizontalContainer;
import Client_Java.net.team6.boggled.client.gui.text.BoggledText;
import Client_Java.net.team6.boggled.client.gui.tools.Alignment;
import Client_Java.net.team6.boggled.client.state.State;
import Client_Java.net.team6.boggled.run.Connect;

import java.sql.SQLException;

public class BoggledWaitingTimer extends HorizontalContainer {

    private final BoggledText gameTime;

    public BoggledWaitingTimer() {
        super();
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new BoggledText("", 32);
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        this.gameTime.setText(Connect.boggledImpl.getWaitingTime(Connect.cref));
    }
}
