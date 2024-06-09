package net.team6.boggled.client.gui.container;

import Client_Java.net.team6.boggled.client.gui.component.BoggledComponent;
import Client_Java.net.team6.boggled.client.gui.container.BoggledContainer;
import Client_Java.net.team6.boggled.common.core.Position;
import Client_Java.net.team6.boggled.common.core.Size;

public class VerticalContainer extends BoggledContainer {
    @Override
    protected Size calculateContentSize() {
        int combinedChildHeight = 0;
        int widestChildWidth = 0;

        for(BoggledComponent boggledComponent : children) {
            combinedChildHeight += boggledComponent.getSize().getHeight() + boggledComponent.getMargin().getVertical();

            if(boggledComponent.getSize().getWidth() > widestChildWidth) {
                widestChildWidth = boggledComponent.getSize().getWidth();
            }
        }

        return new Size(widestChildWidth, combinedChildHeight);
    }

    @Override
    protected void calculateContentPosition() {
        int currentY = padding.getTop();
        int currentX = padding.getLeft();

        for(BoggledComponent boggledComponent : children) {
            if(centerChildren) {
                currentX = getSize().getWidth() / 2 - boggledComponent.getSize().getWidth() / 2;
            }

            currentY += boggledComponent.getMargin().getTop();
            boggledComponent.setRelativePosition(new Position(currentX, currentY));
            boggledComponent.setAbsolutePosition(new Position(currentX + absolutePosition.intX(), currentY + absolutePosition.intY()));
            currentY += boggledComponent.getSize().getHeight();
            currentY += boggledComponent.getMargin().getBottom();
        }
    }
}