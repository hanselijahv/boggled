package net.team6.boggled.client.state.entry;

import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.entry.elements.UILogin;
import net.team6.boggled.client.state.home.elements.UIHomeMenu;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.gui.container.UIContainer;
import net.team6.boggled.common.input.Input;

public class EntryState extends State {


    public EntryState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        uiCanvas.addUIComponent(new UILogin());

    }

    public void enterMenu(UIContainer container) {
        uiCanvas.clear();
        uiCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }
}
