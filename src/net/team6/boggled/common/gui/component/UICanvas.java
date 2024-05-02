package net.team6.boggled.common.gui.component;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.gui.container.AlignableContainer;

public class UICanvas extends AlignableContainer {
    public UICanvas(Size fixedSize) {
        super(fixedSize);
    }

    @Override
    protected void generateSprite() {}
}
