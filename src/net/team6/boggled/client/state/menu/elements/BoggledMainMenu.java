package net.team6.boggled.client.state.menu.elements;

import BoggledApp.BoggledServant;
import BoggledApp.NotLoggedInException;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.state.ingame.InGameState;
import net.team6.boggled.client.state.entry.EntryState;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.text.BoggledHeader;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.utilities.SessionManager;

public class BoggledMainMenu extends VerticalContainer {


    public BoggledMainMenu() {

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0, 0, 50, 0));
        final BoggledHeader header = new BoggledHeader("Boggled", 120);
        header.setPadding(new Spacing(0, 0, 50, 0));
        addUIComponent(header);

        BoggledServant boggledServant = BoggledServant.getInstance();
        String sessionId = SessionManager.getSessionId();

        addUIComponent(new BoggledButton("PLAY GAME", 16, (state) -> new InGameState(state.getGameSettings())));
        addUIComponent(new BoggledButton("OPTIONS", 16, (state) -> ((MenuState) state).enterMenu(new BoggledOptionMenu(state.getGameSettings()))));
        addUIComponent(new BoggledButton("LOGOUT", 16, (state) -> {
            try {
                boggledServant.logout(sessionId); // Call logout with the session ID
                state.setNextState(new EntryState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
            } catch (NotLoggedInException e) {
                System.err.println("Session ID " + sessionId + " is not valid.");
            }
        }));

        addUIComponent(new BoggledButton("EXIT", 16, (state) -> ((MenuState) state).enterMenu(new BoggledExitMenu())));
    }
}