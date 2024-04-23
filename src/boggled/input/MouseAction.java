package boggled.input;

import boggled.state.State;
import boggled.ui.UIImage;

public abstract class MouseAction implements MouseConsumer{

    public abstract void update(State state);
    public abstract UIImage getSprite();
    public abstract void cleanUp();
}
