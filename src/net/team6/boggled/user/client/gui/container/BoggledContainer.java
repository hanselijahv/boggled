    package net.team6.boggled.user.client.gui.container;

    import net.team6.boggled.common.core.Position;
    import net.team6.boggled.common.core.Size;
    import net.team6.boggled.user.client.gui.tools.Alignment;
    import net.team6.boggled.user.client.gui.tools.Spacing;
    import net.team6.boggled.user.client.gui.component.BoggledComponent;
    import net.team6.boggled.user.client.state.State;
    import net.team6.boggled.utilities.ImageUtils;

    import java.awt.*;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public abstract class BoggledContainer extends BoggledComponent {

        protected Color backgroundColor;

        protected Alignment alignment;
        protected boolean centerChildren;

        protected Size fixedSize;

        protected List<BoggledComponent> children;
        protected List<Component> components;
        protected Image sprite;
        protected float opacity;

        public BoggledContainer() {
            super();
            centerChildren = false;
            alignment = new Alignment(Alignment.Position.START, Alignment.Position.START);
            backgroundColor = new Color(0, 0, 0, 0);
            margin = new Spacing(5);
            padding = new Spacing(5);
            children = new ArrayList<>();
            components = new ArrayList<>();
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
        public void update(State state) throws SQLException {
            List<BoggledComponent> copyOfChildren = new ArrayList<>(children);
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

            for(BoggledComponent boggledComponent : children) {
                graphics.drawImage(
                        boggledComponent.getSprite(),
                        boggledComponent.getRelativePosition().intX(),
                        boggledComponent.getRelativePosition().intY(),
                        null
                );
            }

            graphics.dispose();
        }


        public void addUIComponent(BoggledComponent boggledComponent) {
            children.add(boggledComponent);
            boggledComponent.setParent(this);
        }

        public void addComponent(Component component) {
            components.add(component);
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

        public boolean hasComponent(BoggledComponent component) {
            return children.contains(component);
        }

        public void clear() {
            children.clear();
        }

        public void removeComponent(BoggledComponent component) {
            children.remove(component);
        }

        public List<BoggledComponent> getComponents() {
            return children;
        }

    }
