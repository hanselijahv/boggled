package net.team6.boggled.server.state.users.elements;

import net.team6.boggled.common.core.Value;
import net.team6.boggled.common.db.AccountDAO;
import net.team6.boggled.common.model.Account;
import net.team6.boggled.server.gui.container.ServerContainer;
import net.team6.boggled.server.gui.container.ServerHorizontalContainer;
import net.team6.boggled.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.server.gui.input.ServerTextInput;
import net.team6.boggled.server.gui.text.ServerHeader;
import net.team6.boggled.server.gui.text.ServerText;
import net.team6.boggled.server.gui.tools.Alignment;
import net.team6.boggled.server.gui.tools.Spacing;
import net.team6.boggled.server.gui.clickable.ServerButton;
import net.team6.boggled.server.state.users.UsersState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateUsersUI extends ServerVerticalContainer {
    private Value<String> id;
    private Value<String> username;
    private Value<String> password;
    private DefaultTableModel tableModel;
    private JTable table;

    public UpdateUsersUI() throws SQLException {
        final ServerHeader header = new ServerHeader("Update/Remove Users", 80);
        header.setMargin(new Spacing(0, 0, 50, 0));
        addUIComponent(header);

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.id = new Value<>("");
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowVerticalLines(false);
        tableModel.addColumn("Player ID");
        tableModel.addColumn("Username");
        tableModel.addColumn("Password");

        ServerContainer contentContainer = new ServerVerticalContainer();

        ServerTextInput idInput = new ServerTextInput("ID", id);
        idInput.setMargin(new Spacing(0, 0, 10, 0));
        idInput.setEditable(false);

        ServerTextInput usernameInput = new ServerTextInput("USERNAME", username);
        usernameInput.setMargin(new Spacing(0, 0, 10, 0)); // Add bottom margin to create spacing

        ServerTextInput passwordInput = new ServerTextInput("PASSWORD", password);
        passwordInput.setMargin(new Spacing(10, 0, 0, 0)); // Add top margin to create spacing

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                idInput.clearText();
                usernameInput.clearText();
                passwordInput.clearText();
                id.setValue((String) tableModel.getValueAt(row, 0));
                username.setValue((String) tableModel.getValueAt(row, 1));
                password.setValue((String) tableModel.getValueAt(row, 2));
            }
        });
        addUIComponent(new ServerButton("SHOW USERS", 16, (state) -> {
            populateTable();
            int result = JOptionPane.showConfirmDialog(null, new JScrollPane(table), "Select a User", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                idInput.setText(id.get());
                usernameInput.setText(username.get());
                passwordInput.setText(password.get());
            }
        }));


        ServerContainer messageContainer = new ServerVerticalContainer();
        messageContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        ServerContainer buttonContainer = new ServerHorizontalContainer();
        buttonContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        ServerButton removeButton = new ServerButton("REMOVE", 16, (state) -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this user?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                populateTable();
                String id = this.id.get();
                String username = this.username.get();
                String password = this.password.get();
                Account account = new Account(id, username, password);

                AccountDAO accountDAO = new AccountDAO();
                boolean success = accountDAO.delete(account);
                ServerText message;

                if (success) {
                    message = new ServerText("User Removed Successfully", 15);
                    java.util.Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            idInput.clearText();
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
            }

        });

        ServerButton updateButton = new ServerButton("UPDATE", 16, (state) -> {
            populateTable();
            String id = this.id.get();
            String username = this.username.get();
            String password = this.password.get();
            Account account = new Account(id, username, password);
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
                        idInput.clearText();
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

        });
        removeButton.setMargin(new Spacing(0,10,0,0));
        updateButton.setMargin(new Spacing(0,0,0,10));
        contentContainer.addUIComponent(idInput);
        contentContainer.addUIComponent(usernameInput);
        contentContainer.addUIComponent(passwordInput);

        buttonContainer.addUIComponent(removeButton);
        buttonContainer.addUIComponent(updateButton);
        buttonContainer.setMargin(new Spacing(0, 50,10, 50));
        buttonContainer.setPadding(new Spacing(10));

        contentContainer.addUIComponent(buttonContainer);
        contentContainer.addUIComponent(messageContainer);
        addUIComponent(contentContainer);

        addUIComponent(new ServerButton("BACK", 16, (state) -> state.setNextState(new UsersState(state.getWindowSize(), state.getInput(), state.getBoggledSettings()))));

    }

    private void populateTable() throws SQLException {
        tableModel.setRowCount(0);
        AccountDAO accountDAO = new AccountDAO();
        List<Account> accounts = accountDAO.getAll();
        for (Account account : accounts) {
            tableModel.addRow(new Object[]{account.getPlayerId(), account.getUsername(), account.getPassword()});
        }
    }
}
