package temp.ui;

import temp.core.Position;
import temp.core.Size;
import temp.state.State;
import temp.gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class UIContainer extends UIComponent {

    protected Color backgroundColor;

    protected Alignment alignment;
    protected boolean centerChildren;

    protected Size fixedSize;

    protected List<UIComponent> children;
    protected Image sprite;

    public UIContainer() {
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
        if (alignment.getHorizontal().equals(Alignment.Position.CENTER)) {
            x = parent.getSize().getWidth() / 2 - size.getWidth() / 2;
        }
        if (alignment.getHorizontal().equals(Alignment.Position.END)) {
            x = parent.getSize().getWidth() - size.getWidth() - margin.getRight();
        }

        int y = margin.getTop();
        if (alignment.getVertical().equals(Alignment.Position.CENTER)) {
            y = parent.getSize().getHeight() / 2 - size.getHeight() / 2;
        }
        if (alignment.getVertical().equals(Alignment.Position.END)) {
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
    public void update(State state) {
        //children.forEach(component -> component.update(state));
        List<UIComponent> copyOfChildren = new ArrayList<>(children);
        copyOfChildren.forEach(component -> component.update(state));
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

        for (UIComponent uiComponent : children) {
            graphics.drawImage(
                    uiComponent.getSprite(),
                    uiComponent.getRelativePosition().intX(),
                    uiComponent.getRelativePosition().intY(),
                    null
            );
        }

        graphics.dispose();
    }


    public void clear() {
        children.clear();
    }

    public void addUIComponent(UIComponent uiComponent) {
        children.add(uiComponent);
        uiComponent.setParent(this);
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

    public boolean hasComponent(UIComponent component) {
        return children.contains(component);
    }

    public void removeComponent(UIComponent component) {
        children.remove(component);
    }

    public List<UIComponent> getComponents() {
        return children;
    }
}
