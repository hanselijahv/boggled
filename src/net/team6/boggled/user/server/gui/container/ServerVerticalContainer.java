package net.team6.boggled.user.server.gui.container;

import net.team6.boggled.common.core.Position;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.server.gui.component.ServerComponent;

public class ServerVerticalContainer extends ServerContainer {
    @Override
    protected Size calculateContentSize() {
        int combinedChildHeight = 0;
        int widestChildWidth = 0;

        for(ServerComponent serverComponent : children) {
            combinedChildHeight += serverComponent.getSize().getHeight() + serverComponent.getMargin().getVertical();

            if(serverComponent.getSize().getWidth() > widestChildWidth) {
                widestChildWidth = serverComponent.getSize().getWidth();
            }
        }

        return new Size(widestChildWidth, combinedChildHeight);
    }

    @Override
    protected void calculateContentPosition() {
        int currentY = padding.getTop();
        int currentX = padding.getLeft();

        for(ServerComponent serverComponent : children) {
            if(centerChildren) {
                currentX = getSize().getWidth() / 2 - serverComponent.getSize().getWidth() / 2;
            }

            currentY += serverComponent.getMargin().getTop();
            serverComponent.setRelativePosition(new Position(currentX, currentY));
            serverComponent.setAbsolutePosition(new Position(currentX + absolutePosition.intX(), currentY + absolutePosition.intY()));
            currentY += serverComponent.getSize().getHeight();
            currentY += serverComponent.getMargin().getBottom();
        }
    }
}