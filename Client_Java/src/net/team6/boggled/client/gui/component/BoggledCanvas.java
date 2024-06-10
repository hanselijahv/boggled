package net.team6.boggled.client.gui.component;

import net.team6.boggled.client.gui.container.AlignableContainer;
import net.team6.boggled.common.core.Size;

public class BoggledCanvas extends AlignableContainer {
    public BoggledCanvas(Size fixedSize) {
        super(fixedSize);
    }

    @Override
    protected void generateSprite() {}
}
