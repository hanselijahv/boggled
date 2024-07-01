package net.team6.boggled.client.state.waiting.elements;

import net.team6.boggled.client.gui.container.HorizontalContainer;
import net.team6.boggled.client.gui.text.BoggledText;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.waiting.WaitingState;
import net.team6.boggled.run.Connect;

import java.sql.SQLException;

public class BoggledWaitingTimer extends HorizontalContainer {

    private final BoggledText gameTime;
    private final WaitingState waitingState; // Add this line

    public BoggledWaitingTimer(WaitingState waitingState) { // Modify this line
        super();
        this.waitingState = waitingState; // Add this line
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new BoggledText("", 32);
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        String waitingTime = Connect.boggledImpl.getWaitingTime(Connect.cref);
        if(waitingTime.equals("00")) {
            waitingState.endWaiting();
        }
        this.gameTime.setText(waitingTime);
    }
}
