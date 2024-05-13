package net.team6.boggled.server.gui.component;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.server.gui.container.ServerAlignableContainer;

public class ServerCanvas extends ServerAlignableContainer {
    public ServerCanvas(Size fixedSize) {
        super(fixedSize);
    }

    @Override
    protected void generateSprite() {}
}
