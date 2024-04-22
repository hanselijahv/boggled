package temp.ui.clickable;

import temp.core.Size;
import temp.state.State;
import temp.ui.UIContainer;
import temp.ui.UIText;
import temp.ui.VerticalContainer;

import java.awt.*;

public class UIButton extends UIClickable {

    private UIContainer container;
    private UIText label;

    private ClickAction clickAction;

    public UIButton(String label, int fontSize, ClickAction clickAction) {
        this.label = new UIText(label, fontSize);
        this.clickAction = clickAction;

        container = new VerticalContainer(new Size(0, 0));
        container.addUIComponent(this.label);
        container.setFixedSize(new Size(150, 30));
    }

    @Override
    public void update(State state) {
        super.update(state);
        container.update(state);
        size = container.getSize();

        Color color = Color.GRAY;

        if(hasFocus) {
            color = Color.LIGHT_GRAY;
        }

        if(isPressed) {
            color = Color.DARK_GRAY;
        }

        container.setBackgroundColor(color);
    }

    @Override
    protected void onClick(State state) {
        clickAction.execute(state);
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }


}

