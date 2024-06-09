package net.team6.boggled.client.state.entry;

import Client_Java.net.team6.boggled.client.game.settings.GameSettings;
import Client_Java.net.team6.boggled.client.gui.container.BoggledContainer;
import Client_Java.net.team6.boggled.client.input.Input;
import Client_Java.net.team6.boggled.client.state.State;
import Client_Java.net.team6.boggled.client.state.entry.elements.BoggledLogin;
import Client_Java.net.team6.boggled.common.core.Size;

public class EntryState extends State {


    public EntryState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        boggledCanvas.addUIComponent(new BoggledLogin());


    }

    public void enterMenu(BoggledContainer container) {
        boggledCanvas.clear();
        boggledCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }
}
