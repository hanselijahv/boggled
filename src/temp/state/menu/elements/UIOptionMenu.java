package temp.state.menu.elements;

import temp.core.Size;
import temp.state.menu.MenuState;
import temp.ui.Alignment;
import temp.ui.UIContainer;
import temp.ui.UIText;
import temp.ui.VerticalContainer;
import temp.ui.clickable.UIButton;

public class UIOptionMenu extends VerticalContainer {
    public UIOptionMenu(Size windowSize) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);


        addUIComponent(new UIText("OPTIONS", 16));
        addUIComponent(new UIButton("BACK", 16, (state) -> ((MenuState)state).enterMenu(new UIMainMenu(windowSize))));
    }
}

