package net.team6.boggled.client.gui.component;

import net.team6.boggled.client.core.Position;
import net.team6.boggled.client.core.Size;
import net.team6.boggled.client.gui.Spacing;
import net.team6.boggled.client.gui.container.UIContainer;
import net.team6.boggled.client.state.State;

import java.awt.*;
import java.sql.SQLException;

public abstract class UIComponent {

    protected Position relativePosition;
    protected Position absolutePosition;
    protected Size size;
    protected Spacing margin;
    protected Spacing padding;

    protected UIContainer parent;

    public UIComponent() {
        relativePosition = new Position(0, 0);
        absolutePosition = new Position(0, 0);
        size = new Size(1, 1);
        margin = new Spacing(0);
        padding = new Spacing(0);
    }

    public abstract Image getSprite();
    public abstract void update(State state) throws SQLException;

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

    public void setParent(UIContainer parent) {
        this.parent = parent;
    }
}
