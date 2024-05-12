package net.team6.boggled.user.client.state.entry.elements;

import net.team6.boggled.common.exceptions.UserNotFoundException;
import net.team6.boggled.user.client.gui.text.BoggledHeader;
import net.team6.boggled.user.client.gui.text.BoggledText;
import net.team6.boggled.user.client.gui.tools.Spacing;
import net.team6.boggled.user.client.state.menu.MenuState;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.user.client.gui.clickable.BoggledButton;
import net.team6.boggled.user.client.gui.container.BoggledContainer;
import net.team6.boggled.user.client.gui.container.VerticalContainer;
import net.team6.boggled.user.client.gui.input.BoggledTextInput;
import net.team6.boggled.user.client.gui.tools.Alignment;
import net.team6.boggled.user.server.ServerTest;

public class BoggledLogin extends VerticalContainer {
    private final Value<String> username;
    private final Value<String> password;

    public BoggledLogin() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final BoggledHeader header = new BoggledHeader("Boggled", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        BoggledContainer contentContainer = new VerticalContainer();
        BoggledTextInput usernameInput = new BoggledTextInput("USERNAME", username);
        usernameInput.setMargin(new Spacing(0, 0, 10, 0));
        contentContainer.addUIComponent(usernameInput);

        BoggledTextInput passwordInput = new BoggledTextInput("PASSWORD", password);
        passwordInput.setMargin(new Spacing(10, 0, 0, 0));
        contentContainer.addUIComponent(passwordInput);

        BoggledContainer buttonContainer = new VerticalContainer();
        buttonContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        BoggledButton loginButton = new BoggledButton("LOGIN", 16, (state) -> {
            try {
                ServerTest.login(username.get(), password.get());
                state.setNextState(new MenuState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
            } catch (UserNotFoundException e) {
                System.err.println("Login failed: " + e.getMessage());
            }
        });

        buttonContainer.addUIComponent(loginButton);
        buttonContainer.setMargin(new Spacing(0));
        buttonContainer.setPadding(new Spacing(10));

        addUIComponent(contentContainer);
        addUIComponent(buttonContainer);
    }
}

