package net.team6.boggled.user.server.state.users.elements;

import net.team6.boggled.common.core.Value;
import net.team6.boggled.common.db.AccountDAO;
import net.team6.boggled.common.model.Account;
import net.team6.boggled.user.server.dev.Server;
import net.team6.boggled.user.server.gui.text.ServerText;
import net.team6.boggled.user.server.state.menu.elements.ServerUsersMenu;
import net.team6.boggled.user.server.gui.container.ServerContainer;
import net.team6.boggled.user.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.user.server.gui.input.ServerTextInput;
import net.team6.boggled.user.server.gui.tools.Alignment;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.gui.clickable.ServerButton;
import net.team6.boggled.user.server.state.menu.ServerMenuState;
import net.team6.boggled.user.server.gui.text.ServerHeader;
import net.team6.boggled.user.server.state.users.UsersState;

import java.util.Timer;
import java.util.TimerTask;

public class CreateUsersUI extends ServerVerticalContainer {
    private Value<String> username;
    private Value<String> password;

    public CreateUsersUI() {
        final ServerHeader header = new ServerHeader("Create a User", 80);
        header.setMargin(new Spacing(0, 0, 50, 0));
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;

        ServerContainer contentContainer = new ServerVerticalContainer();
        ServerTextInput usernameInput = new ServerTextInput("USERNAME", username);
        usernameInput.setMargin(new Spacing(0, 0, 10, 0)); // Add bottom margin to create spacing


        ServerTextInput passwordInput = new ServerTextInput("PASSWORD", password);
        passwordInput.setMargin(new Spacing(10, 0, 0, 0)); // Add top margin to create spacing


        ServerContainer messageContainer = new ServerVerticalContainer();
        messageContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        ServerContainer buttonContainer = new ServerVerticalContainer();
        buttonContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        buttonContainer.addUIComponent(new ServerButton("CREATE", 16, (state) -> {
            String username = this.username.get();
            String password = this.password.get();
            Account account = new Account(null, username, password);

            AccountDAO accountDAO = new AccountDAO();
            boolean success = accountDAO.insert(account);

            ServerText message;

            if (success) {
                message = new ServerText("User Created Successfully", 15);
                Timer timer = new Timer();
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
                message = new ServerText("Failed to create user", 15);
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
        buttonContainer.addUIComponent(new ServerButton("BACK", 16, (state) -> ((UsersState) state).enterMenu(new ServerUsersMenu())));
        buttonContainer.setMargin(new Spacing(0, 0,10, 0));
        buttonContainer.setPadding(new Spacing(10));


        addUIComponent(header);
        contentContainer.addUIComponent(usernameInput);
        contentContainer.addUIComponent(passwordInput);
        addUIComponent(contentContainer);
        addUIComponent(buttonContainer);
        addUIComponent(messageContainer);

    }
}
