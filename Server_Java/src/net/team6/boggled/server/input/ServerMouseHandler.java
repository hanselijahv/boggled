package net.team6.boggled.server.input;


import net.team6.boggled.server.gui.component.BoggledImage;
import net.team6.boggled.server.state.ServerState;

import java.sql.SQLException;
import java.util.Optional;

public class ServerMouseHandler {

    private ServerMouseAction primaryButtonAction;
    private ServerMouseAction rightButtonAction;
    private ServerMouseAction wheelButtonAction;
    private ServerMouseConsumer activeConsumer;

    public void update(ServerState state) throws SQLException {
        final ServerInput serverInput = state.getInput();

        handlePrimaryButton(state);
        handleRightButton(state);
        handleWheelButton(state);
        handleActiveConsumer(state, serverInput);

        cleanUp(serverInput);
    }



    private void handleWheelButton(ServerState state) throws SQLException {
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

    private void handleRightButton(ServerState state) throws SQLException {
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

    private void handlePrimaryButton(ServerState state) {
        if(primaryButtonAction != null) {
            setActiveConsumer(primaryButtonAction);
            primaryButtonAction.update(state);
        }
    }

    private void cleanUp(ServerInput serverInput) {
        if(!serverInput.isMousePressed()) {
            activeConsumer = null;
        }

        serverInput.cleanUpInputEvents();
    }

    private void handleActiveConsumer(ServerState state, ServerInput serverInput) throws SQLException {
        if(activeConsumer != null) {
            if(serverInput.isMouseClicked()) {
                activeConsumer.onClick(state);
            }

            if (serverInput.isMousePressed()) {
                activeConsumer.onDrag(state);
            }

            if(serverInput.isMouseReleased()) {
                activeConsumer.onRelease(state);
            }
        }
    }

    public ServerMouseConsumer getActiveConsumer() {
        return activeConsumer;
    }

    public void setActiveConsumer(ServerMouseConsumer serverMouseConsumer) {
        if(activeConsumer == null) {
            activeConsumer = serverMouseConsumer;
        }
    }

    public void switchPrimaryButtonAction(ServerMouseAction mouseAction) {
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
