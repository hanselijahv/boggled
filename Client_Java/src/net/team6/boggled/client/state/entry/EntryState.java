package net.team6.boggled.client.state.entry;

import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.entry.elements.BoggledLogin;
import net.team6.boggled.common.core.Size;

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
