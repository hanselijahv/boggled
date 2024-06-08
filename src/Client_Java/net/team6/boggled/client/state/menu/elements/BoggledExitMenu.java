package Client_Java.net.team6.boggled.client.state.menu.elements;

import Client_Java.net.team6.boggled.client.gui.clickable.BoggledButton;
import Client_Java.net.team6.boggled.client.gui.container.VerticalContainer;
import Client_Java.net.team6.boggled.client.gui.text.BoggledHeader;
import Client_Java.net.team6.boggled.client.gui.tools.Alignment;
import Client_Java.net.team6.boggled.client.gui.tools.Spacing;
import Client_Java.net.team6.boggled.client.state.menu.MenuState;

public class BoggledExitMenu extends VerticalContainer {

    public BoggledExitMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final BoggledHeader header = new BoggledHeader("EXIT?", 80);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new BoggledButton("YES", 16, (state) -> System.exit(0)));
        addUIComponent(new BoggledButton("NO", 16, (state) -> ((MenuState) state).enterMenu(new BoggledMainMenu())));

    }

}
