package Client_Java.net.team6.boggled.client.state.menu.elements;

import Client_Java.net.team6.boggled.client.gui.clickable.BoggledButton;
import Client_Java.net.team6.boggled.client.gui.container.VerticalContainer;
import Client_Java.net.team6.boggled.client.gui.text.BoggledHeader;
import Client_Java.net.team6.boggled.client.gui.tools.Alignment;
import Client_Java.net.team6.boggled.client.gui.tools.Spacing;
import Client_Java.net.team6.boggled.client.state.entry.EntryState;
import Client_Java.net.team6.boggled.client.state.leaderboards.elements.BoggledLeaderboards;
import Client_Java.net.team6.boggled.client.state.menu.MenuState;
import Client_Java.net.team6.boggled.client.state.waiting.WaitingState;
import Client_Java.net.team6.boggled.run.Connect;

public class BoggledMainMenu extends VerticalContainer {


    public BoggledMainMenu() {

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0, 0, 50, 0));
        final BoggledHeader header = new BoggledHeader("Boggled", 120);
        header.setPadding(new Spacing(0, 0, 50, 0));
        addUIComponent(header);

        addUIComponent(new BoggledButton("PLAY GAME", 16, (state) -> {
            Connect.boggledImpl.joinWaitingRoom(Connect.username);
            state.setNextState(new WaitingState(state.getWindowSize(), state.getInput(), state.getGameSettings()));


        }));

        addUIComponent(new BoggledButton("LEADERBOARDS", 16, (state) -> ((MenuState) state).enterMenu(new BoggledLeaderboards())));
        addUIComponent(new BoggledButton("OPTIONS", 16, (state) -> ((MenuState) state).enterMenu(new BoggledOptionMenu(state.getGameSettings()))));
        addUIComponent(new BoggledButton("LOGOUT", 16, (state) -> {
            try {
                // TODO
                //Connect.boggledImpl.logout(Connect.sessionID);
                state.setNextState(new EntryState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }));

        addUIComponent(new BoggledButton("EXIT", 16, (state) -> ((MenuState) state).enterMenu(new BoggledExitMenu())));
    }
}