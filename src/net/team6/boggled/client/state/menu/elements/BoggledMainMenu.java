package net.team6.boggled.client.state.menu.elements;

import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.state.ingame.InGameState;
import net.team6.boggled.client.state.entry.EntryState;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.text.BoggledHeader;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.run.Connect;

import java.awt.*;
import java.io.IOException;

public class BoggledMainMenu extends VerticalContainer {


    public BoggledMainMenu() {

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0, 0, 50, 0));
        final BoggledHeader header = new BoggledHeader("Boggled", 120);
        header.setPadding(new Spacing(0, 0, 50, 0));
        addUIComponent(header);



        addUIComponent(new BoggledButton("PLAY GAME", 16, (state) -> {
            try {
                new InGameState(state.getGameSettings());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            }
        }));
        addUIComponent(new BoggledButton("OPTIONS", 16, (state) -> ((MenuState) state).enterMenu(new BoggledOptionMenu(state.getGameSettings()))));
        addUIComponent(new BoggledButton("LOGOUT", 16, (state) -> {
            try {
                Connect.boggledImpl.logout(Connect.sessionID);
                state.setNextState(new EntryState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }));

        addUIComponent(new BoggledButton("EXIT", 16, (state) -> ((MenuState) state).enterMenu(new BoggledExitMenu())));
    }
}