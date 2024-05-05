package net.team6.boggled.user.server.state.users.elements;

import net.team6.boggled.common.core.Value;
import net.team6.boggled.common.db.AccountDAO;
import net.team6.boggled.common.model.Account;
import net.team6.boggled.user.server.state.menu.elements.ServerUsersMenu;
import net.team6.boggled.user.server.gui.container.ServerContainer;
import net.team6.boggled.user.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.user.server.gui.input.ServerTextInput;
import net.team6.boggled.user.server.gui.tools.Alignment;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.gui.clickable.ServerButton;
import net.team6.boggled.user.server.state.menu.ServerMenuState;
import net.team6.boggled.user.server.gui.text.ServerHeader;

public class CreateUsersUI extends ServerVerticalContainer {
    private Value<String> username;
    private Value<String> password;

    public CreateUsersUI() {
        final ServerHeader header = new ServerHeader("Create a User", 80);
        header.setMargin(new Spacing(0, 0, 50, 0));
        addUIComponent(header);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;

        ServerContainer contentContainer = new ServerVerticalContainer();
        ServerTextInput usernameInput = new ServerTextInput("USERNAME", username);
        usernameInput.setMargin(new Spacing(0, 0, 10, 0)); // Add bottom margin to create spacing
        contentContainer.addUIComponent(usernameInput);

        ServerTextInput passwordInput = new ServerTextInput("PASSWORD", password);
        passwordInput.setMargin(new Spacing(10, 0, 0, 0)); // Add top margin to create spacing
        contentContainer.addUIComponent(passwordInput);

        ServerContainer buttonContainer = new ServerVerticalContainer();
        buttonContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        buttonContainer.addUIComponent(new ServerButton("CREATE", 16, (state) -> {
            String username = this.username.get();
            String password = this.password.get();
            Account account = new Account(null, username, password);

            AccountDAO accountDAO = new AccountDAO();
            boolean success = accountDAO.insert(account);

            if (success) {
                state.setNextState(new ServerMenuState(state.getWindowSize(), state.getInput(), state.getBoggledSettings()));
            } else {
                System.out.println("Failed to create user");
            }

        }));
        buttonContainer.addUIComponent(new ServerButton("BACK", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerUsersMenu())));
        buttonContainer.setMargin(new Spacing(0));
        buttonContainer.setPadding(new Spacing(10));

        addUIComponent(contentContainer);
        addUIComponent(buttonContainer);

    }
}
