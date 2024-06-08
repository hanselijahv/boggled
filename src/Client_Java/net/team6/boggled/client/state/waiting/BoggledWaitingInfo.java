package Client_Java.net.team6.boggled.client.state.waiting;

import Client_Java.net.team6.boggled.client.gui.container.HorizontalContainer;
import Client_Java.net.team6.boggled.client.gui.text.BoggledText;
import Client_Java.net.team6.boggled.client.gui.tools.Alignment;
import Client_Java.net.team6.boggled.client.state.State;
import Client_Java.net.team6.boggled.run.Connect;

import java.sql.SQLException;

public class BoggledWaitingInfo extends HorizontalContainer {

    private final BoggledText gameTime;

    public BoggledWaitingInfo() {
        super();
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new BoggledText("", 32);
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        this.gameTime.setText(Connect.boggledImpl.getWaitingRoomInfo(Connect.username));
    }
}
