package net.team6.boggled.client.state.home.elements;

import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.gui.container.UIContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.home.HomeState;
import net.team6.boggled.client.gui.clickable.UIButton;
import net.team6.boggled.client.gui.clickable.UICheckbox;
import net.team6.boggled.client.gui.clickable.UISlider;
import net.team6.boggled.client.gui.text.UIText;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.utilities.BoggledColors;

import java.sql.SQLException;

public class UIHomeOptionMenu extends VerticalContainer {
    private final UISlider musicVolSlider;
    private final UIText musicVolLabel;
    private final UISlider soundVolSlider;
    private final UIText soundVolLabel;


    public UIHomeOptionMenu(GameSettings settings) {

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        musicVolSlider = new UISlider(0, 100);
        musicVolSlider.setValue(settings.getAudioSettings().getMusicVolume());
        musicVolLabel = new UIText("", 18);

        soundVolSlider = new UISlider(0, 100);
        soundVolSlider.setValue(settings.getAudioSettings().getSoundVolume());
        soundVolLabel = new UIText("", 18);

        UIContainer labelContainer = new VerticalContainer();

        labelContainer.addUIComponent(new UICheckbox("FULLSCREEN", settings.isFullScreenMode()));
        labelContainer.setMargin(new Spacing(0));
        labelContainer.setPadding(new Spacing(10));

        UIContainer contentContainer = new VerticalContainer();
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setPadding(new Spacing(10));

        contentContainer.addUIComponent(musicVolLabel);
        contentContainer.addUIComponent(musicVolSlider);
        contentContainer.addUIComponent(soundVolLabel);
        contentContainer.addUIComponent(soundVolSlider);

        contentContainer.addUIComponent(new UIButton("BACK", 16, (state) -> ((HomeState) state).enterMenu(new UIHomeMenu())));

        setBackgroundColor(BoggledColors.MENU_BACKGROUND_COLOR);
        addUIComponent(labelContainer);
        addUIComponent(contentContainer);
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        handleVolume(state);
    }

    private void handleVolume(State state) {
        int musicVolume = (int) musicVolSlider.getValue();
        int soundVolume = (int) soundVolSlider.getValue();


        state.getGameSettings().getAudioSettings().setMusicVolume(musicVolume);
        musicVolLabel.setText(String.format("MUSIC VOL: %d", musicVolume));

        state.getGameSettings().getAudioSettings().setSoundVolume(soundVolume);
        soundVolLabel.setText(String.format("SOUND EFFECT VOL: %d", soundVolume));
    }
}

