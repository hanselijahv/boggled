package net.team6.boggled.client.gui.clickable;

import net.team6.boggled.client.state.State;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public interface ClickAction {
    void execute(State state) throws SQLException, IOException, FontFormatException;
}
