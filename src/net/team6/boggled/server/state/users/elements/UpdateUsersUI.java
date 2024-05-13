package net.team6.boggled.server.state.users.elements;

import net.team6.boggled.common.core.Value;
import net.team6.boggled.common.db.AccountDAO;
import net.team6.boggled.common.model.Account;
import net.team6.boggled.server.gui.component.ServerComponent;
import net.team6.boggled.server.gui.container.ServerContainer;
import net.team6.boggled.server.gui.container.ServerHorizontalContainer;
import net.team6.boggled.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.server.gui.input.ServerTextInput;
import net.team6.boggled.server.gui.text.ServerHeader;
import net.team6.boggled.server.gui.text.ServerText;
import net.team6.boggled.server.gui.tools.Alignment;
import net.team6.boggled.server.gui.tools.Spacing;
import net.team6.boggled.server.gui.clickable.ServerButton;
import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.server.state.menu.elements.ServerMainMenu;
import net.team6.boggled.server.state.menu.elements.ServerUsersMenu;
import net.team6.boggled.server.state.users.UsersState;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateUsersUI extends ServerVerticalContainer {
    private Value<String> username;
    private Value<String> password;

    public UpdateUsersUI() {
        AccountDAO accountDB = new AccountDAO();
        final ServerHeader header = new ServerHeader("Update/Remove Users", 80);
        header.setMargin(new Spacing(0, 0, 50, 0));
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;
        ServerHorizontalContainer hc = new ServerHorizontalContainer();
        hc.setMargin(new Spacing(0,10,0,10));

        ServerContainer tableContainer = new ServerHorizontalContainer();


        ServerContainer contentContainer = new ServerVerticalContainer();
        ServerTextInput usernameInput = new ServerTextInput("USERNAME", username);
        usernameInput.setMargin(new Spacing(0, 0, 10, 0)); // Add bottom margin to create spacing

        ServerTextInput passwordInput = new ServerTextInput("PASSWORD", password);
        passwordInput.setMargin(new Spacing(10, 0, 0, 0)); // Add top margin to create spacing

        ServerContainer messageContainer = new ServerVerticalContainer();
        messageContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        ServerContainer buttonContainer = new ServerHorizontalContainer();
        buttonContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        buttonContainer.addUIComponent(new ServerButton("REMOVE", 16, (state) -> {
            String username = this.username.get();
            String password = this.password.get();
            Account account = new Account(null, username, password);

            AccountDAO accountDAO = new AccountDAO();
            boolean success = accountDAO.delete(account);
            ServerText message;

            if (success) {
                message = new ServerText("User Removed Successfully", 15);
                java.util.Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        usernameInput.clearText();
                        passwordInput.clearText();
                        messageContainer.removeComponent(message);
                        timer.cancel();
                    }
                }, 2000);


            } else {
                message = new ServerText("Failed to remove user", 15);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        messageContainer.removeComponent(message);
                        timer.cancel();
                    }
                }, 3000);
            }

            message.setMargin(new Spacing(0));
            messageContainer.addUIComponent(message);

        }));
        buttonContainer.addUIComponent(new ServerButton("UPDATE", 16, (state) -> {
            String username = this.username.get();
            String password = this.password.get();
            Account account = new Account(null, username, password);
            String[] params = {account.getUsername(), account.getPassword()};

            AccountDAO accountDAO = new AccountDAO();
            boolean success = accountDAO.update(account, params);

            ServerText message;

            if (success) {
                message = new ServerText("User Updated Successfully", 15);
                java.util.Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        usernameInput.clearText();
                        passwordInput.clearText();
                        messageContainer.removeComponent(message);
                        timer.cancel();
                    }
                }, 2000);


            } else {
                message = new ServerText("Failed to update user", 15);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        messageContainer.removeComponent(message);
                        timer.cancel();
                    }
                }, 3000);
            }

            message.setMargin(new Spacing(0));
            messageContainer.addUIComponent(message);

        }));
        buttonContainer.setMargin(new Spacing(0, 50,10, 50));

        addUIComponent(header);
        hc.addUIComponent(tableContainer);
        contentContainer.addUIComponent(usernameInput);
        contentContainer.addUIComponent(passwordInput);
        contentContainer.addUIComponent(buttonContainer);
        contentContainer.addUIComponent(messageContainer);
        hc.addUIComponent(contentContainer);

        addUIComponent(hc);

        addUIComponent(new ServerButton("BACK", 16, (state) -> state.setNextState(new UsersState(state.getWindowSize(), state.getInput(), state.getBoggledSettings()))));



    }
}
