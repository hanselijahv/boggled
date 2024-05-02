package net.team6.boggled.user.client.input;

import net.team6.boggled.user.client.state.State;
import net.team6.boggled.user.client.gui.component.BoggledImage;

import java.sql.SQLException;
import java.util.Optional;

public class MouseHandler {

    private MouseAction primaryButtonAction;
    private MouseAction rightButtonAction;
    private MouseAction wheelButtonAction;
    private MouseConsumer activeConsumer;

    public void update(State state) throws SQLException {
        final Input input = state.getInput();

        handlePrimaryButton(state);
        handleRightButton(state);
        handleWheelButton(state);
        handleActiveConsumer(state, input);

        cleanUp(input);
    }



    private void handleWheelButton(State state) throws SQLException {
        if(wheelButtonAction != null) {
            wheelButtonAction.update(state);
            if(state.getInput().isWheelMouseClicked()) {
                wheelButtonAction.onClick(state);
            }

            if (state.getInput().isWheelMousePressed()) {
                wheelButtonAction.onDrag(state);
            }

            if(state.getInput().isWheelMouseReleased()) {
                wheelButtonAction.onRelease(state);
            }
        }
    }

    private void handleRightButton(State state) throws SQLException {
        if(rightButtonAction != null) {
            rightButtonAction.update(state);
            if(state.getInput().isRightMouseClicked()) {
                rightButtonAction.onClick(state);
            }

            if (state.getInput().isRightMousePressed()) {
                rightButtonAction.onDrag(state);
            }

            if(state.getInput().isRightMouseReleased()) {
                rightButtonAction.onRelease(state);
            }
        }
    }

    private void handlePrimaryButton(State state) {
        if(primaryButtonAction != null) {
            setActiveConsumer(primaryButtonAction);
            primaryButtonAction.update(state);
        }
    }

    private void cleanUp(Input input) {
        if(!input.isMousePressed()) {
            activeConsumer = null;
        }

        input.cleanUpInputEvents();
    }

    private void handleActiveConsumer(State state, Input input) throws SQLException {
        if(activeConsumer != null) {
            if(input.isMouseClicked()) {
                activeConsumer.onClick(state);
            }

            if (input.isMousePressed()) {
                activeConsumer.onDrag(state);
            }

            if(input.isMouseReleased()) {
                activeConsumer.onRelease(state);
            }
        }
    }

    public MouseConsumer getActiveConsumer() {
        return activeConsumer;
    }

    public void setActiveConsumer(MouseConsumer mouseConsumer) {
        if(activeConsumer == null) {
            activeConsumer = mouseConsumer;
        }
    }

    public void switchPrimaryButtonAction(MouseAction mouseAction) {
        if(primaryButtonAction != null) {
            primaryButtonAction.cleanUp();
        }

        primaryButtonAction = mouseAction;
    }

    public Optional<BoggledImage> getPrimaryButtonUI() {
        if(primaryButtonAction != null) {
            return Optional.ofNullable(primaryButtonAction.getSprite());
        }

        return Optional.empty();
    }

}
