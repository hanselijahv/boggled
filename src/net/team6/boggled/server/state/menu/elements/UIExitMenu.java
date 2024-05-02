package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.common.gui.container.VerticalContainer;
import net.team6.boggled.common.gui.tools.Alignment;
import net.team6.boggled.common.gui.tools.Spacing;
import net.team6.boggled.common.gui.clickable.UIButton;
import net.team6.boggled.common.gui.text.UIHeader;

public class UIExitMenu extends VerticalContainer {
    public UIExitMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final UIHeader header = new UIHeader("EXIT?", 80);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new UIButton("YES", 16, (state) -> System.exit(0)));
        addUIComponent(new UIButton("NO", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerUIMainMenu())));

    }
}
