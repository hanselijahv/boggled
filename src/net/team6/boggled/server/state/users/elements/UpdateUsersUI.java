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
import net.team6.boggled.utilities.BoggledColors;
import net.team6.boggled.utilities.FontUtils;
import net.team6.boggled.utilities.OptionPaneButtonUI;
import net.team6.boggled.utilities.StyledButtonUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateUsersUI extends ServerVerticalContainer {
    private Value<String> id;
    private Value<String> username;
    private Value<String> password;
    private DefaultTableModel tableModel;
    private JTable table;
    private Font font = FontUtils.loadFont("/font/MP16REG.ttf", 16);

    public UpdateUsersUI() {
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

        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setBorder(null);
                }
                return c;
            }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowVerticalLines(false);
        tableModel.addColumn("Player ID");
        tableModel.addColumn("Username");
        tableModel.addColumn("Password");


        table.setBackground(BoggledColors.SYSTEM_COLOR);
        table.setForeground(BoggledColors.PRIMARY_COLOR);
        table.setFont(font);
        table.setGridColor(BoggledColors.SYSTEM_COLOR);
        table.setRowHeight(25);
        table.setSelectionBackground(BoggledColors.TABLE_HIGHLIGHTED_COLOR);


        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(BoggledColors.MENU_BACKGROUND_COLOR);
        tableHeader.setForeground(BoggledColors.PRIMARY_COLOR);
        tableHeader.setFont(font);

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
            UIManager.put("OptionPane.background", BoggledColors.SYSTEM_COLOR);
            UIManager.put("Panel.background", BoggledColors.SYSTEM_COLOR);
            UIManager.put("OptionPane.messageFont", font);
            UIManager.put("OptionPane.messageForeground", BoggledColors.PRIMARY_COLOR);

            populateTable();

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(null);
            scrollPane.getViewport().setBackground(BoggledColors.SYSTEM_COLOR);

            JButton okButton = getOkButton(idInput, usernameInput, passwordInput);

            JButton cancelButton = getCancelButton();

            Object[] options = {okButton, cancelButton};
            JOptionPane.showOptionDialog(null, scrollPane, "Select a User", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        }));


        ServerContainer messageContainer = new ServerVerticalContainer();
        messageContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        ServerContainer buttonContainer = new ServerHorizontalContainer();
        buttonContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));

        ServerButton removeButton = new ServerButton("REMOVE", 16, (state) -> {
            populateTable();
            JButton yesButton = new JButton("Yes");
            yesButton.setFont(font);
            yesButton.setBackground(BoggledColors.BUTTON_COLOR);
            yesButton.setForeground(BoggledColors.PRIMARY_COLOR);
            yesButton.setUI(new OptionPaneButtonUI());
            yesButton.addActionListener(e -> {
                String id = this.id.get();
                Account account = new Account(id, null, null);
                AccountDAO accountDAO = new AccountDAO();
                try {
                    boolean success = accountDAO.delete(account);
                    if (success) {
                        populateTable();
                        ServerText message = new ServerText("User Removed Successfully", 15);
                        message.setMargin(new Spacing(0));
                        messageContainer.addUIComponent(message);
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
                        ServerText message = new ServerText("Failed to remove user", 15);
                        message.setMargin(new Spacing(0));
                        messageContainer.addUIComponent(message);
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                messageContainer.removeComponent(message);
                                timer.cancel();
                            }
                        }, 3000);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                Window window = SwingUtilities.getWindowAncestor(yesButton);
                if (window != null) {
                    window.dispose();
                }
            });

            JButton noButton = new JButton("No");
            noButton.setFont(font);
            noButton.setBackground(BoggledColors.BUTTON_COLOR);
            noButton.setForeground(BoggledColors.PRIMARY_COLOR);
            noButton.setUI(new OptionPaneButtonUI());
            noButton.addActionListener(e -> {
                Window window = SwingUtilities.getWindowAncestor(noButton);
                if (window != null) {
                    window.dispose();
                }
            });

            Object[] options = {yesButton, noButton};
            JOptionPane.showOptionDialog(null, "Are you sure you want to remove this user?", "Remove User", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

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
        removeButton.setMargin(new Spacing(0, 10, 0, 0));
        updateButton.setMargin(new Spacing(0, 0, 0, 10));
        contentContainer.addUIComponent(idInput);
        contentContainer.addUIComponent(usernameInput);
        contentContainer.addUIComponent(passwordInput);

        buttonContainer.addUIComponent(removeButton);
        buttonContainer.addUIComponent(updateButton);
        buttonContainer.setMargin(new Spacing(0, 50, 10, 50));
        buttonContainer.setPadding(new Spacing(10));


        addUIComponent(contentContainer);
        addUIComponent(buttonContainer);
        contentContainer.addUIComponent(messageContainer);

        addUIComponent(new ServerButton("BACK", 16, (state) -> state.setNextState(new UsersState(state.getWindowSize(), state.getInput(), state.getBoggledSettings()))));

    }

    private JButton getCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(font);
        cancelButton.setBackground(BoggledColors.BUTTON_COLOR);
        cancelButton.setForeground(BoggledColors.PRIMARY_COLOR);
        cancelButton.setUI(new OptionPaneButtonUI());
        cancelButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(cancelButton);
            if (window != null) {
                window.dispose();
            }
        });
        return cancelButton;
    }

    private JButton getOkButton(ServerTextInput idInput, ServerTextInput usernameInput, ServerTextInput passwordInput) {
        JButton okButton = new JButton("Ok");
        okButton.setFont(font);
        okButton.setBackground(BoggledColors.BUTTON_COLOR);
        okButton.setForeground(BoggledColors.PRIMARY_COLOR);
        okButton.setUI(new OptionPaneButtonUI());
        okButton.addActionListener(e -> {
            idInput.setText(id.get());
            usernameInput.setText(username.get());
            passwordInput.setText(password.get());
            // Close the JOptionPane
            Window window = SwingUtilities.getWindowAncestor(okButton);
            if (window != null) {
                window.dispose();
            }
        });
        return okButton;
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
