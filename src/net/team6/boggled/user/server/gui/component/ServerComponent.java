package net.team6.boggled.user.server.gui.component;


import net.team6.boggled.common.core.Position;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.server.gui.container.ServerContainer;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.state.ServerState;

import java.awt.*;
import java.sql.SQLException;

public abstract class ServerComponent {

    protected Position relativePosition;
    protected Position absolutePosition;
    protected Size size;
    protected Spacing margin;
    protected Spacing padding;

    protected ServerContainer parent;

    public ServerComponent() {
        relativePosition = new Position(0, 0);
        absolutePosition = new Position(0, 0);
        size = new Size(1, 1);
        margin = new Spacing(0);
        padding = new Spacing(0);
    }

    public abstract Image getSprite();

    public abstract void update(ServerState state) throws SQLException;

    public Position getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Position relativePosition) {
        this.relativePosition = relativePosition;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Spacing getMargin() {
        return margin;
    }

    public void setMargin(Spacing margin) {
        this.margin = margin;
    }

    public Spacing getPadding() {
        return padding;
    }

    public void setPadding(Spacing padding) {
        this.padding = padding;
    }

    public Position getAbsolutePosition() {
        return absolutePosition;
    }

    public void setAbsolutePosition(Position absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public void setParent(ServerContainer parent) {
        this.parent = parent;
    }
}
