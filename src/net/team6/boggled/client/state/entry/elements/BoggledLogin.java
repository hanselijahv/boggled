package net.team6.boggled.client.state.entry.elements;

import BoggledApp.BoggledServant;
import BoggledApp.UserNotFoundException;
import net.team6.boggled.client.gui.text.BoggledHeader;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.input.BoggledTextInput;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.utilities.SessionManager;

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

        BoggledContainer buttonContainer = getBoggledButtonContainer();

        addUIComponent(contentContainer);
        addUIComponent(buttonContainer);
    }

    private BoggledContainer getBoggledButtonContainer() {
        BoggledContainer buttonContainer = new VerticalContainer();
        buttonContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        BoggledButton loginButton = getBoggledButton();


        buttonContainer.addUIComponent(loginButton);
        buttonContainer.addUIComponent(new BoggledButton("EXIT", 16, (state) -> {
            System.exit(0);
        }));
        buttonContainer.setMargin(new Spacing(0));
        buttonContainer.setPadding(new Spacing(10));
        return buttonContainer;
    }

    private BoggledButton getBoggledButton() {
        BoggledServant boggledServant = BoggledServant.getInstance();

        return new BoggledButton("LOGIN", 16, (state) -> {
            String user = username.get();
            String pass = password.get();

            try {
                boggledServant.login(user, pass);
                String sessionId = boggledServant.getSessionId(user);
                System.out.println("Session ID: " + sessionId);
                SessionManager.setSessionId(sessionId);
                state.setNextState(new MenuState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
            } catch (UserNotFoundException e) {
                System.err.println("User '" + user + "' not found or invalid credentials.");
            }
        });
    }
}

