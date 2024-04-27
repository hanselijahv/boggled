package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.gui.tools.Spacing;
import net.team6.boggled.client.gui.clickable.UIButton;
import net.team6.boggled.client.gui.text.UIHeader;

public class UIExitMenu extends VerticalContainer {
    public UIExitMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final UIHeader header = new UIHeader("Shut down Boggled?", 80);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new UIButton("YES", 16, (state) -> System.exit(0)));
        addUIComponent(new UIButton("NO", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerUIMainMenu())));

    }
}
