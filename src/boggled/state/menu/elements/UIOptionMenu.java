package boggled.state.menu.elements;

import boggled.game.settings.GameSettings;
import boggled.state.State;
import boggled.state.menu.MenuState;
import boggled.ui.*;
import boggled.ui.clickable.UIButton;
import boggled.ui.clickable.UISlider;
import boggled.ui.text.UIText;

public class UIOptionMenu extends VerticalContainer {
    private final UISlider musicVolSlider;
    private final UIText musicVolLabel;
    private final UISlider soundVolSlider;
    private final UIText soundVolLabel;


    public UIOptionMenu(GameSettings settings) {

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        musicVolSlider = new UISlider(0, 1);
        musicVolSlider.setValue(settings.getAudioSettings().getMusicVolume());
        musicVolLabel = new UIText("", 18);

        soundVolSlider = new UISlider(0, 1);
        soundVolSlider.setValue(settings.getAudioSettings().getSoundVolume());
        soundVolLabel = new UIText("", 18);


        UIContainer contentContainer = new VerticalContainer();
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setPadding(new Spacing(10));
        contentContainer.addUIComponent(musicVolLabel);
        contentContainer.addUIComponent(musicVolSlider);
        contentContainer.addUIComponent(soundVolLabel);
        contentContainer.addUIComponent(soundVolSlider);

        contentContainer.addUIComponent(new UIButton("BACK", 16, (state) -> ((MenuState)state).enterMenu(new UIMainMenu())));

        addUIComponent(contentContainer);
    }

    @Override
    public void update(State state) {
        super.update(state);
        handleVolume(state);
    }

    private void handleVolume(State state) {
        state.getGameSettings().getAudioSettings().setMusicVolume((float) musicVolSlider.getValue());
        musicVolLabel.setText(String.format("MUSIC VOL: %d", Math.round(musicVolSlider.getValue() * 100)));

        state.getGameSettings().getAudioSettings().setSoundVolume((float) soundVolSlider.getValue());
        soundVolLabel.setText(String.format("SOUND EFFECT VOL: %d", Math.round(soundVolSlider.getValue() * 100)));
    }
}

