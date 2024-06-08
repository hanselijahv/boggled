package Client_Java.net.team6.boggled.client.state.entry.elements;

import BoggledApp.AlreadyLoggedInException;
import BoggledApp.UserNotFoundException;
import Client_Java.net.team6.boggled.client.gui.clickable.BoggledButton;
import Client_Java.net.team6.boggled.client.gui.container.BoggledContainer;
import Client_Java.net.team6.boggled.client.gui.container.VerticalContainer;
import Client_Java.net.team6.boggled.client.gui.input.BoggledTextInput;
import Client_Java.net.team6.boggled.client.gui.text.BoggledHeader;
import Client_Java.net.team6.boggled.client.gui.tools.Alignment;
import Client_Java.net.team6.boggled.client.gui.tools.Spacing;
import Client_Java.net.team6.boggled.client.state.menu.MenuState;
import Client_Java.net.team6.boggled.common.core.Value;
import Client_Java.net.team6.boggled.run.Connect;

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

        return new BoggledButton("LOGIN", 16, (state) -> {
            String user = username.get();
            String pass = password.get();

            try {
                Connect.boggledImpl.login(user, pass);
                Connect.username = user;
                //TODO
                //Connect.sessionID = Connect.boggledImpl.getSessionId(user);
                state.setNextState(new MenuState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
            } catch (UserNotFoundException e) {
                System.err.println("User '" + user + "' not found or invalid credentials.");
            } catch (AlreadyLoggedInException e) {
                System.err.println("User '" + user + "' already logged in.");
            }
        });
    }
}

