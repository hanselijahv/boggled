package net.team6.boggled.user.server.gui.container;

import net.team6.boggled.common.core.Position;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.server.gui.component.ServerComponent;
import net.team6.boggled.user.server.gui.tools.Alignment;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.state.ServerState;
import net.team6.boggled.utilities.ImageUtils;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerContainer extends ServerComponent {

    protected Color backgroundColor;

    protected Alignment alignment;
    protected boolean centerChildren;

    protected Size fixedSize;

    protected List<ServerComponent> children;
    protected Image sprite;
    protected float opacity;

    public ServerContainer() {
        super();
        centerChildren = false;
        alignment = new Alignment(Alignment.Position.START, Alignment.Position.START);
        backgroundColor = new Color(0, 0, 0, 0);
        margin = new Spacing(5);
        padding = new Spacing(5);
        children = new ArrayList<>();
    }

    protected abstract Size calculateContentSize();
    protected abstract void calculateContentPosition();

    private void calculateSize() {
        Size calculatedContentSize = calculateContentSize();
        size = fixedSize != null
                ? fixedSize
                : new Size(
                padding.getHorizontal() + calculatedContentSize.getWidth(),
                padding.getVertical() + calculatedContentSize.getHeight());
    }

    private void calculatePosition() {
        int x = margin.getLeft();
        if(alignment.getHorizontal().equals(Alignment.Position.CENTER)) {
            x = parent.getSize().getWidth() / 2 - size.getWidth() / 2;
        }
        if(alignment.getHorizontal().equals(Alignment.Position.END)) {
            x = parent.getSize().getWidth() - size.getWidth() - margin.getRight();
        }

        int y = margin.getTop();
        if(alignment.getVertical().equals(Alignment.Position.CENTER)) {
            y = parent.getSize().getHeight() / 2 - size.getHeight() / 2;
        }
        if(alignment.getVertical().equals(Alignment.Position.END)) {
            y = parent.getSize().getHeight() - size.getHeight() - margin.getBottom();
        }

        this.relativePosition = new Position(x, y);
        calculateContentPosition();
    }


    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public void update(ServerState state) throws SQLException {
        List<ServerComponent> copyOfChildren = new ArrayList<>(children);
        copyOfChildren.forEach(component -> {
            try {
                component.update(state);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        calculateSize();
        calculatePosition();

        if (state.getTime().secondsDividableBy(0.05)) {
            generateSprite();
        }
    }

    protected void generateSprite() {
        sprite = ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = (Graphics2D) sprite.getGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        for(ServerComponent serverComponent : children) {
            graphics.drawImage(
                    serverComponent.getSprite(),
                    serverComponent.getRelativePosition().intX(),
                    serverComponent.getRelativePosition().intY(),
                    null
            );
        }

        graphics.dispose();
    }


    public void addUIComponent(ServerComponent serverComponent) {
        children.add(serverComponent);
        serverComponent.setParent(this);
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setFixedSize(Size fixedSize) {
        this.fixedSize = fixedSize;
    }

    public void setCenterChildren(boolean centerChildren) {
        this.centerChildren = centerChildren;
    }

    public boolean hasComponent(ServerComponent component) {
        return children.contains(component);
    }

    public void clear() {
        children.clear();
    }

    public void removeComponent(ServerComponent component) {
        children.remove(component);
    }

    public List<ServerComponent> getComponents() {
        return children;
    }

    public void setDimmedBackground(Color color) {
        backgroundColor = color;
        generateSprite();
    }
}
