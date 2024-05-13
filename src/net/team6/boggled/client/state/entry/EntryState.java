package net.team6.boggled.client.state.entry;

import BoggledApp.Boggled;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.entry.elements.BoggledLogin;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.input.Input;

public class EntryState extends State {


    public EntryState(Size windowSize, Input input, GameSettings gameSettings, Boggled boggledImpl) {
        super(windowSize, input, gameSettings);

        boggledCanvas.addUIComponent(new BoggledLogin(boggledImpl));


    }

    public void enterMenu(BoggledContainer container) {
        boggledCanvas.clear();
        boggledCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }
}
