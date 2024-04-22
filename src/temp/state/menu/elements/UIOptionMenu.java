package temp.state.menu.elements;

import temp.core.Size;
import temp.game.settings.GameSettings;
import temp.state.State;
import temp.state.menu.MenuState;
import temp.ui.*;
import temp.ui.clickable.UIButton;
import temp.ui.clickable.UISlider;

import java.awt.*;

public class UIOptionMenu extends VerticalContainer {
    private UISlider musicVolSlider;
    private UIText musicVolLabel;


    public UIOptionMenu(Size windowSize) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        musicVolSlider = new UISlider(0, 1);
        musicVolLabel = new UIText("", 16);

        addUIComponent(new UIText("OPTIONS",16));
        addUIComponent(musicVolLabel);
        addUIComponent(musicVolSlider);
        addUIComponent(new UIButton("BACK", 16, (state) -> ((MenuState)state).enterMenu(new UIMainMenu(windowSize))));
    }

    @Override
    public void update(State state) {
        super.update(state);
        handleVolume(state);
    }

    private void handleVolume(State state) {
        state.getGameSettings().getAudioSettings().setMusicVolume((float) musicVolSlider.getValue());
        musicVolLabel.setText(String.format("MUSIC VOL: %d", Math.round(musicVolSlider.getValue() * 100)));
    }
}

