package Client_Java.net.team6.boggled.client.gui.container;

import Client_Java.net.team6.boggled.common.core.Position;
import Client_Java.net.team6.boggled.common.core.Size;

public class AlignableContainer extends BoggledContainer {

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
