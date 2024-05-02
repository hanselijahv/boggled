package test.game;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.client.gui.component.BoggledComponent;
import net.team6.boggled.user.client.gui.container.BoggledContainer;
import net.team6.boggled.user.client.gui.container.VerticalContainer;
import net.team6.boggled.user.client.state.State;

import java.awt.*;
import java.sql.SQLException;

public class ViewAdapter extends BoggledContainer {
    private final View view;

    private BoggledContainer container;

    public ViewAdapter(View view) {
        this.view = view;
        container = new VerticalContainer();
    }

    @Override
    protected Size calculateContentSize() {
        return null;
    }

    @Override
    protected void calculateContentPosition() {

    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    public void update(State state) throws SQLException {

    }
}
