package net.team6.boggled.client.state.entry.elements;

import BoggledApp.AlreadyLoggedInException;
import BoggledApp.UserNotFoundException;
import net.team6.boggled.client.display.Display;
import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.input.BoggledTextInput;
import net.team6.boggled.client.gui.text.BoggledHeader;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.common.core.Value;
import net.team6.boggled.run.Connect;
import net.team6.boggled.utilities.BoggledColors;
import net.team6.boggled.utilities.FontUtils;
import net.team6.boggled.utilities.OptionPaneButtonUI;

import javax.swing.*;
import java.awt.*;

public class BoggledLogin extends VerticalContainer {
    private final Value<String> username;
    private final Value<String> password;

    private Font font = FontUtils.loadFont("/font/MP16REG.ttf", 16);

    public BoggledLogin() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;

        setPadding(new Spacing(0, 0, 50, 0));
        final BoggledHeader header = new BoggledHeader("Boggled", 120);
        header.setPadding(new Spacing(0, 0, 50, 0));
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

            UIManager.put("OptionPane.background", BoggledColors.SYSTEM_COLOR);
            UIManager.put("Panel.background", BoggledColors.SYSTEM_COLOR);
            UIManager.put("OptionPane.messageFont", font);
            UIManager.put("OptionPane.messageForeground", BoggledColors.PRIMARY_COLOR);

            JOptionPane optionPane;

            try {
                Connect.boggledImpl.login(user, pass);
                Connect.username = user;
                //TODO
                //Connect.sessionID = Connect.boggledImpl.getSessionId(user);
                state.setNextState(new MenuState(state.getWindowSize(), state.getInput(), state.getGameSettings()));
            } catch (UserNotFoundException e) {
                optionPane = new JOptionPane(
                        "User " + user + " not found or invalid credentials.",
                        JOptionPane.ERROR_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        new Object[]{getOkButton()},
                        null
                );
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                dialog.setUndecorated(true);
            } catch (AlreadyLoggedInException e) {
                optionPane = new JOptionPane(
                        "User " + user + " already logged in",
                        JOptionPane.WARNING_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        new Object[]{getOkButton()},
                        null
                );
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                dialog.setUndecorated(true);
            } catch (Exception e) {
                optionPane = new JOptionPane(
                        "An error occurred while trying to login.",
                        JOptionPane.ERROR_MESSAGE,
                        JOptionPane.OK_OPTION,
                        null,
                        new Object[]{getOkButton()},
                        null
                );
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                dialog.setUndecorated(true);
            }
        });
    }

    private JButton getOkButton() {
        JButton okButton = new JButton("OK");
        okButton.setFont(font);
        okButton.setBackground(BoggledColors.BUTTON_COLOR);
        okButton.setForeground(BoggledColors.PRIMARY_COLOR);
        okButton.setUI(new OptionPaneButtonUI());
        okButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(okButton);
            if (window != null) {
                window.dispose();
            }
        });
        return okButton;
    }
}

