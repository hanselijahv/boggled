package net.team6.boggled.client.state.entry.elements;

import net.team6.boggled.client.state.entry.EntryState;
import net.team6.boggled.client.state.home.HomeState;
import net.team6.boggled.client.state.home.elements.UIHomeMenu;
import net.team6.boggled.client.state.waiting.WaitingState;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.common.gui.clickable.UIButton;
import net.team6.boggled.common.gui.container.UIContainer;
import net.team6.boggled.common.gui.container.VerticalContainer;
import net.team6.boggled.common.gui.input.UITextInput;
import net.team6.boggled.common.gui.tools.Alignment;

public class UILogin extends VerticalContainer {
    private final Value<String> username;
    private final Value<String> password;

    public UILogin() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;

        UIContainer contentContainer = new VerticalContainer();

        contentContainer.addUIComponent(new UITextInput("USERNAME", username));
        contentContainer.addUIComponent(new UITextInput("PASSWORD", password));
        contentContainer.addUIComponent(new UIButton("BACK", 16, (state) -> state.setNextState(new HomeState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));

        addUIComponent(contentContainer);

    }
}
