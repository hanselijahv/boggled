package net.team6.boggled.user.client.state.entry;

import net.team6.boggled.user.client.game.settings.GameSettings;
import net.team6.boggled.user.client.state.State;
import net.team6.boggled.user.client.state.entry.elements.BoggledLogin;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.client.gui.container.BoggledContainer;
import net.team6.boggled.user.client.input.Input;

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
