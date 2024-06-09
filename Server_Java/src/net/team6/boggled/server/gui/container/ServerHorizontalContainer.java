package net.team6.boggled.server.gui.container;

import net.team6.boggled.common.core.Position;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.server.gui.component.ServerComponent;

public class ServerHorizontalContainer extends ServerContainer {
    @Override
    protected Size calculateContentSize() {
        int combinedChildWidth = 0;
        int tallestChildHeight = 0;

        for(ServerComponent serverComponent : children) {
            combinedChildWidth += serverComponent.getSize().getWidth() + serverComponent.getMargin().getHorizontal();

            if(serverComponent.getSize().getHeight() > tallestChildHeight) {
                tallestChildHeight = serverComponent.getSize().getHeight();
            }
        }

        return new Size(combinedChildWidth, tallestChildHeight);
    }

    @Override
    protected void calculateContentPosition() {
        int currentX = padding.getLeft();
        int currentY = padding.getTop();

        for(ServerComponent serverComponent : children) {
            if(centerChildren) {
                currentY = getSize().getHeight() / 2 - serverComponent.getSize().getHeight() / 2;
            }

            currentX += serverComponent.getMargin().getLeft();
            serverComponent.setRelativePosition(new Position(currentX, currentY));
            serverComponent.setAbsolutePosition(new Position(currentX + absolutePosition.intX(), currentY + absolutePosition.intY()));
            currentX += serverComponent.getSize().getWidth();
            currentX += serverComponent.getMargin().getRight();
        }
    }
}
