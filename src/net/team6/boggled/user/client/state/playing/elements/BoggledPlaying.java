package net.team6.boggled.user.client.state.playing.elements;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.client.gui.clickable.BoggledButton;
import net.team6.boggled.user.client.gui.container.BoggledContainer;
import net.team6.boggled.user.client.gui.container.HorizontalContainer;
import net.team6.boggled.user.client.gui.container.VerticalContainer;
import net.team6.boggled.user.client.gui.text.BoggledText;
import net.team6.boggled.user.client.gui.tools.Alignment;
import net.team6.boggled.user.client.gui.tools.Spacing;

import java.util.List;

import static test.game.View.generateRandomLetters;

public class BoggledPlaying extends VerticalContainer {

    public BoggledPlaying() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        BoggledContainer outerContainer = new VerticalContainer();
        int gridSize = 10; // Number of columns and rows
        int squareSize = 50; // Size of each square component

        for (int row = 0; row < 2; row++) {
            BoggledContainer rowContainer = new HorizontalContainer();
            List<Character> randomLetters = generateRandomLetters();// Create a new row container
            for (int col = 0; col < gridSize; col++) {
                BoggledText text = new BoggledText(String.valueOf(randomLetters.get(col)), 16);
                text.setPadding(new Spacing(5)); // Adjust padding as needed
                text.setSize(new Size(squareSize, squareSize)); // Set square size
                rowContainer.addUIComponent(text);
            }
            outerContainer.addUIComponent(rowContainer); // Add the row container to the outer container
        }

        addUIComponent(outerContainer);
    }


}
