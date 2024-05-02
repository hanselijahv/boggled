package net.team6.boggled.user.client.state.playing;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.client.game.settings.GameSettings;
import net.team6.boggled.user.client.input.Input;
import net.team6.boggled.user.client.state.State;
import net.team6.boggled.user.client.state.playing.elements.BoggledPlaying;
import test.game.View;

import java.awt.*;
import java.io.IOException;

public class PlayState extends State {


    public PlayState(Size windowSize, Input input, GameSettings gameSettings) throws IOException, FontFormatException {
        super(windowSize, input, gameSettings);


        // Add the content panel to boggledCanvas
        boggledCanvas.addUIComponent(new BoggledPlaying());
    }

    @Override
    protected void handleInput() {
        // Handle input here
    }


}
