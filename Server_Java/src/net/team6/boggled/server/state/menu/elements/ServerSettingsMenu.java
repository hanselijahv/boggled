package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.common.db.SettingsDAO;
import net.team6.boggled.common.model.Settings;
import net.team6.boggled.server.dev.settings.ServerSettings;
import net.team6.boggled.server.gui.clickable.ServerButton;
import net.team6.boggled.server.gui.clickable.ServerSlider;
import net.team6.boggled.server.gui.container.ServerContainer;
import net.team6.boggled.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.server.gui.text.ServerText;
import net.team6.boggled.server.gui.tools.Alignment;
import net.team6.boggled.server.gui.tools.Spacing;
import net.team6.boggled.server.state.ServerState;
import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.utilities.BoggledColors;

import java.sql.SQLException;

public class ServerSettingsMenu extends ServerVerticalContainer {
    private final ServerSlider waitTimeSlider;
    private final ServerSlider roundTimeSlider;
    private final ServerSlider numRoundsSlider;
    private final ServerText waitTimeText;
    private final ServerText roundTimeText;
    private final ServerText numRoundsText;

    public ServerSettingsMenu(ServerSettings settings) {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        waitTimeSlider = new ServerSlider(10, 60);
        waitTimeSlider.setValue(settings.getGameSettings().getWaitingTime());
        waitTimeText = new ServerText("", 18);

        roundTimeSlider = new ServerSlider(30, 120);
        roundTimeSlider.setValue(settings.getGameSettings().getRoundTime());
        roundTimeText = new ServerText("", 18);

        numRoundsSlider = new ServerSlider(3, 5);
        numRoundsSlider.setValue(settings.getGameSettings().getRoundsToWin());
        numRoundsText = new ServerText("", 18);


        ServerContainer contentContainer = new ServerVerticalContainer();
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setPadding(new Spacing(10));

        contentContainer.addUIComponent(waitTimeText);
        contentContainer.addUIComponent(waitTimeSlider);
        contentContainer.addUIComponent(roundTimeText);
        contentContainer.addUIComponent(roundTimeSlider);
        contentContainer.addUIComponent(numRoundsText);
        contentContainer.addUIComponent(numRoundsSlider);



        contentContainer.addUIComponent(new ServerButton("BACK", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerMainMenu())));

        setBackgroundColor(BoggledColors.SYSTEM_COLOR);
        addUIComponent(contentContainer);

    }

    public void update(ServerState state) throws SQLException {
        super.update(state);
        handleSettings(state);
    }


    private void handleSettings(ServerState state) throws SQLException {

        int wTime = (int) waitTimeSlider.getValue();
        int rTime = (int) roundTimeSlider.getValue();
        int nRounds = (int) numRoundsSlider.getValue();

        String[] params = {String.valueOf(wTime), String.valueOf(rTime), String.valueOf(nRounds)};

        Settings oldSettings = state.getBoggledSettings().getGameSettings();
        SettingsDAO settingsDAO = new SettingsDAO();
        settingsDAO.update(oldSettings, params);

        state.getBoggledSettings().getGameSettings().setWaitingTime(wTime);
        waitTimeText.setText(String.format("WAIT TIME: %ds", wTime));

        state.getBoggledSettings().getGameSettings().setRoundTime(rTime);
        roundTimeText.setText(String.format("ROUND TIME: %ds", rTime));

        state.getBoggledSettings().getGameSettings().setRoundsToWin(nRounds);
        numRoundsText.setText(String.format("NUM ROUNDS: %d", nRounds));
    }

}
