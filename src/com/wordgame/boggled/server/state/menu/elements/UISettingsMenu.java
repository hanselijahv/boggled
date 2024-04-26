package com.wordgame.boggled.server.state.menu.elements;

import com.wordgame.boggled.server.state.menu.ServerMenuState;
import com.wordgame.boggled.server.state.settings.BoggledSettings;
import com.wordgame.boggled.state.State;
import com.wordgame.boggled.ui.*;
import com.wordgame.boggled.ui.VerticalContainer;
import com.wordgame.boggled.ui.clickable.UIButton;
import com.wordgame.boggled.ui.clickable.UICheckbox;
import com.wordgame.boggled.ui.clickable.UISlider;
import com.wordgame.boggled.ui.text.UIText;
import com.wordgame.db.old.SettingsDB;
import com.wordgame.references.Settings;

import java.awt.*;

public class UISettingsMenu extends VerticalContainer {
    private final UISlider waitTimeSlider;
    private final UISlider roundTimeSlider;
    private final UISlider numRoundsSlider;
    private final UIText waitTimeText;
    private final UIText roundTimeText;
    private final UIText numRoundsText;

    public UISettingsMenu(BoggledSettings settings) {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        waitTimeSlider = new UISlider(10, 61);
        waitTimeSlider.setValue(settings.getGameSettings().getWaitingTime());
        waitTimeText = new UIText("", 18);

        roundTimeSlider = new UISlider(30, 121);
        roundTimeSlider.setValue(settings.getGameSettings().getRoundTime());
        roundTimeText = new UIText("", 18);

        numRoundsSlider = new UISlider(3, 6);
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

    public void update(State state) {
        super.update(state);
        handleSettings(state);
    }


    private void handleSettings(State state) {

        int wTime = (int) waitTimeSlider.getValue();
        int rTime = (int) roundTimeSlider.getValue();
        int nRounds = (int) numRoundsSlider.getValue();

        Settings newSettings = new Settings(wTime, rTime, nRounds);
        SettingsDB settingsDB = new SettingsDB();
        settingsDB.updateSettings(newSettings);

        state.getBoggledSettings().getGameSettings().setRoundTime(wTime);
        waitTimeText.setText(String.format("WAIT TIME: %d", wTime));

        state.getBoggledSettings().getGameSettings().setWaitingTime(rTime);
        roundTimeText.setText(String.format("ROUND TIME: %d", rTime));

        state.getBoggledSettings().getGameSettings().setRoundsToWin(nRounds);
        numRoundsText.setText(String.format("NUM ROUNDS: %d", nRounds));
    }

}
