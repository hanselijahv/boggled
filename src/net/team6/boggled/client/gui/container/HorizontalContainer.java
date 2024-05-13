package net.team6.boggled.client.gui.container;

import net.team6.boggled.common.core.Position;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.client.gui.component.BoggledComponent;

public class HorizontalContainer extends BoggledContainer {
    @Override
    protected Size calculateContentSize() {
        int combinedChildWidth = 0;
        int tallestChildHeight = 0;

        for(BoggledComponent boggledComponent : children) {
            combinedChildWidth += boggledComponent.getSize().getWidth() + boggledComponent.getMargin().getHorizontal();

            if(boggledComponent.getSize().getHeight() > tallestChildHeight) {
                tallestChildHeight = boggledComponent.getSize().getHeight();
            }
        }

        return new Size(combinedChildWidth, tallestChildHeight);
    }

    @Override
    protected void calculateContentPosition() {
        int currentX = padding.getLeft();
        int currentY = padding.getTop();

        for(BoggledComponent boggledComponent : children) {
            if(centerChildren) {
                currentY = getSize().getHeight() / 2 - boggledComponent.getSize().getHeight() / 2;
            }

            currentX += boggledComponent.getMargin().getLeft();
            boggledComponent.setRelativePosition(new Position(currentX, currentY));
            boggledComponent.setAbsolutePosition(new Position(currentX + absolutePosition.intX(), currentY + absolutePosition.intY()));
            currentX += boggledComponent.getSize().getWidth();
            currentX += boggledComponent.getMargin().getRight();
        }
    }
}
