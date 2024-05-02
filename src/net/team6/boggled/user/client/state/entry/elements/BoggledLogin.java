package net.team6.boggled.user.client.state.entry.elements;

import net.team6.boggled.user.client.gui.tools.Spacing;
import net.team6.boggled.user.client.state.menu.MenuState;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.user.client.gui.clickable.BoggledButton;
import net.team6.boggled.user.client.gui.container.BoggledContainer;
import net.team6.boggled.user.client.gui.container.VerticalContainer;
import net.team6.boggled.user.client.gui.input.BoggledTextInput;
import net.team6.boggled.user.client.gui.tools.Alignment;

public class BoggledLogin extends VerticalContainer {
    private final Value<String> username;
    private final Value<String> password;

    public BoggledLogin() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;

        BoggledContainer contentContainer = new VerticalContainer();
        BoggledTextInput usernameInput = new BoggledTextInput("USERNAME", username);
        usernameInput.setMargin(new Spacing(0, 0, 10, 0)); // Add bottom margin to create spacing
        contentContainer.addUIComponent(usernameInput);

        BoggledTextInput passwordInput = new BoggledTextInput("PASSWORD", password);
        passwordInput.setMargin(new Spacing(10, 0, 0, 0)); // Add top margin to create spacing
        contentContainer.addUIComponent(passwordInput);

        BoggledContainer buttonContainer = new VerticalContainer();
        buttonContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        buttonContainer.addUIComponent(new BoggledButton("LOGIN", 16, (state) -> state.setNextState(new MenuState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));
        buttonContainer.setMargin(new Spacing(0));
        buttonContainer.setPadding(new Spacing(10));

        addUIComponent(contentContainer);
        addUIComponent(buttonContainer);
    }
}
