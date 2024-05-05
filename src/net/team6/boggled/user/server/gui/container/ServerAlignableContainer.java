package net.team6.boggled.user.server.gui.container;

import net.team6.boggled.common.core.Position;
import net.team6.boggled.common.core.Size;

public class ServerAlignableContainer extends ServerContainer {

    public ServerAlignableContainer(Size fixedSize) {
        this.fixedSize = fixedSize;
    }

    @Override
    protected Size calculateContentSize() {
        return fixedSize;
    }

    @Override
    protected void calculateContentPosition() {
        children.forEach(child -> child.setAbsolutePosition(new Position(
                absolutePosition.getX() + child.getRelativePosition().getX(),
                absolutePosition.getY() + child.getRelativePosition().getY()
        )));
    }

    public void resize(Size size) {
        fixedSize = size;
    }
}
