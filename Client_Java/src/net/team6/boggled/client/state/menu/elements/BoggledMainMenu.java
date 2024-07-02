package net.team6.boggled.client.state.menu.elements;

import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.text.BoggledHeader;
import net.team6.boggled.client.gui.text.BoggledText;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.client.state.entry.EntryState;
import net.team6.boggled.client.state.leaderboards.elements.BoggledLeaderboards;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.client.state.waiting.WaitingState;
import net.team6.boggled.common.core.Position;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.run.Connect;

public class BoggledMainMenu extends VerticalContainer {

    public BoggledMainMenu() {

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0, 0, 50, 0));
        final BoggledHeader header = new BoggledHeader("Boggled", 120);
        header.setPadding(new Spacing(0, 0, 50, 0));
        addUIComponent(header);

        BoggledText label = new BoggledText("Logged in as: " + Connect.username, 16);
        label.setRelativePosition(new Position(0, 0));
        label.setSize(new Size(100, 20));
        label.setPadding(new Spacing(10,0,50,0));
        label.setRelativePosition(new Position(100,100));

        addUIComponent(label);

        addUIComponent(new BoggledButton("PLAY GAME", 16, (state) -> {
            Connect.boggledImpl.joinWaitingRoom(Connect.username);
            state.setNextState(new WaitingState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
        }));

        addUIComponent(new BoggledButton("LEADERBOARDS", 16, (state) -> ((MenuState) state).enterMenu(new BoggledLeaderboards())));
        addUIComponent(new BoggledButton("OPTIONS", 16, (state) -> ((MenuState) state).enterMenu(new BoggledOptionMenu(state.getGameSettings()))));
        addUIComponent(new BoggledButton("LOGOUT", 16, (state) -> {
            try {
                Connect.boggledImpl.logout(Connect.username);
                state.setNextState(new EntryState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }));


        // addUIComponent(new BoggledButton("EXIT", 16, (state) -> ((MenuState) state).enterMenu(new BoggledExitMenu())));
    }
}