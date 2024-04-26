package net.team6.boggled.client.gui.container;

import net.team6.boggled.client.core.Position;
import net.team6.boggled.client.core.Size;

public class AlignableContainer extends UIContainer {

    public AlignableContainer(Size fixedSize) {
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
