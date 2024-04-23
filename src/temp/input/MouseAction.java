package temp.input;

import temp.state.State;
import temp.ui.UIImage;

public abstract class MouseAction implements MouseConsumer{

    public abstract void update(State state);
    public abstract UIImage getSprite();
    public abstract void cleanUp();
}
