package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.server.state.settings.BoggledSettings;
import net.team6.boggled.client.gui.Alignment;
import net.team6.boggled.client.gui.Spacing;
import net.team6.boggled.client.gui.UIContainer;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.gui.VerticalContainer;
import net.team6.boggled.client.gui.clickable.UIButton;
import net.team6.boggled.client.gui.clickable.UICheckbox;
import net.team6.boggled.client.gui.clickable.UISlider;
import net.team6.boggled.client.gui.text.UIText;
import net.team6.boggled.common.model.Settings;
import net.team6.boggled.common.db.SettingsDAO;

import java.awt.*;
import java.sql.SQLException;

public class UISettingsMenu extends VerticalContainer {
    private final UISlider waitTimeSlider;
    private final UISlider roundTimeSlider;
    private final UISlider numRoundsSlider;
    private final UIText waitTimeText;
    private final UIText roundTimeText;
    private final UIText numRoundsText;

    public UISettingsMenu(BoggledSettings settings) {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        waitTimeSlider = new UISlider(10, 60);
        waitTimeSlider.setValue(settings.getGameSettings().getWaitingTime());
        waitTimeText = new UIText("", 18);

        roundTimeSlider = new UISlider(30, 120);
        roundTimeSlider.setValue(settings.getGameSettings().getRoundTime());
        roundTimeText = new UIText("", 18);

        numRoundsSlider = new UISlider(3, 5);
        numRoundsSlider.setValue(settings.getGameSettings().getRoundsToWin());
        numRoundsText = new UIText("", 18);

        UIContainer labelContainer = new VerticalContainer();
        labelContainer.addUIComponent(new UICheckbox("FULLSCREEN", settings.isFullScreenMode()));
        labelContainer.setMargin(new Spacing(0));
        labelContainer.setPadding(new Spacing(10));

        UIContainer contentContainer = new VerticalContainer();
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setPadding(new Spacing(10));

        contentContainer.addUIComponent(waitTimeText);
        contentContainer.addUIComponent(waitTimeSlider);
        contentContainer.addUIComponent(roundTimeText);
        contentContainer.addUIComponent(roundTimeSlider);
        contentContainer.addUIComponent(numRoundsText);
        contentContainer.addUIComponent(numRoundsSlider);



        contentContainer.addUIComponent(new UIButton("BACK", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerUIMainMenu())));

        setBackgroundColor(Color.decode("#051923"));
        addUIComponent(labelContainer);
        addUIComponent(contentContainer);

    }

    public void update(State state) throws SQLException {
        super.update(state);
        handleSettings(state);
    }


    private void handleSettings(State state) throws SQLException {

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
