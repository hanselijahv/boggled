package com.wordgame.boggled.ui;

import com.wordgame.boggled.core.Position;
import com.wordgame.boggled.core.Size;

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
