package temp.ui;

import temp.core.Position;
import temp.core.Size;
import temp.state.State;
import temp.gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class UIContainer extends UIComponent {

    protected Color backgroundColor;

    protected Alignment alignment;
    protected Size windowSize;
    protected boolean centerChildren;

    protected Size fixedSize;

    protected List<UIComponent> children;
    protected Image sprite;

    public UIContainer(Size windowSize) {
        super();
        centerChildren = false;
        this.windowSize = windowSize;
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
        int x = padding.getLeft();
        if(alignment.getHorizontal().equals(Alignment.Position.CENTER)) {
            x = windowSize.getWidth() / 2 - size.getWidth() / 2;
        }
        if(alignment.getHorizontal().equals(Alignment.Position.END)) {
            x = windowSize.getWidth() - size.getWidth() - margin.getRight();
        }

        int y = padding.getTop();
        if(alignment.getVertical().equals(Alignment.Position.CENTER)) {
            y = windowSize.getHeight() / 2 - size.getHeight() / 2;
        }
        if(alignment.getVertical().equals(Alignment.Position.END)) {
            y = windowSize.getHeight() - size.getHeight() - margin.getBottom();
        }

        this.relativePosition = new Position(x, y);
        this.absolutePosition = new Position(x, y);
        calculateContentPosition();
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    protected void generateSprite(){
        sprite = ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = (Graphics2D) sprite.getGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        for(UIComponent uiComponent : children) {
            graphics.drawImage(
                    uiComponent.getSprite(),
                    uiComponent.getRelativePosition().intX(),
                    uiComponent.getRelativePosition().intY(),
                    null
            );
        }

        graphics.dispose();
    }

    @Override
    public void update(State state) {
        children.forEach(component -> component.update(state));
        calculateSize();
        calculatePosition();

        if(state.getTime().secondsDividableBy(0.05)) {
            generateSprite();
        }
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

}
