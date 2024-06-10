package net.team6.boggled.client.state.menu.elements;

import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.clickable.BoggledCheckbox;
import net.team6.boggled.client.gui.clickable.BoggledSlider;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.text.BoggledText;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.client.state.menu.elements.BoggledMainMenu;
import net.team6.boggled.utilities.BoggledColors;

import java.sql.SQLException;

public class BoggledOptionMenu extends VerticalContainer {
    private final BoggledSlider musicVolSlider;
    private final BoggledText musicVolLabel;
    private final BoggledSlider soundVolSlider;
    private final BoggledText soundVolLabel;


    public BoggledOptionMenu(GameSettings settings) {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        musicVolSlider = new BoggledSlider(0, 100);
        musicVolSlider.setValue(settings.getAudioSettings().getMusicVolume());
        musicVolLabel = new BoggledText("", 18);

        soundVolSlider = new BoggledSlider(0, 100);
        soundVolSlider.setValue(settings.getAudioSettings().getSoundVolume());
        soundVolLabel = new BoggledText("", 18);

        BoggledContainer labelContainer = new VerticalContainer();

        labelContainer.addUIComponent(new BoggledCheckbox("FULLSCREEN", settings.isFullScreenMode()));
        labelContainer.setMargin(new Spacing(0));
        labelContainer.setPadding(new Spacing(10));

        BoggledContainer contentContainer = new VerticalContainer();
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setPadding(new Spacing(10));

        contentContainer.addUIComponent(musicVolLabel);
        contentContainer.addUIComponent(musicVolSlider);
        contentContainer.addUIComponent(soundVolLabel);
        contentContainer.addUIComponent(soundVolSlider);

        contentContainer.addUIComponent(new BoggledButton("BACK", 16, (state) -> ((MenuState) state).enterMenu(new BoggledMainMenu())));

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

