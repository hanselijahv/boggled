package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.server.gui.clickable.ServerButton;
import net.team6.boggled.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.server.gui.text.ServerHeader;
import net.team6.boggled.server.gui.tools.Alignment;
import net.team6.boggled.server.gui.tools.Spacing;
import net.team6.boggled.server.state.menu.ServerMenuState;

public class ServerExitMenu extends ServerVerticalContainer {
    public ServerExitMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final ServerHeader header = new ServerHeader("EXIT?", 80);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new ServerButton("YES", 16, (state) -> System.exit(0)));
        addUIComponent(new ServerButton("NO", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerMainMenu())));

    }
}
