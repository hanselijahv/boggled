package com.wordgame.boggled.input;

import com.wordgame.boggled.core.Position;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    private Position mousePosition;
    private boolean mouseClicked;
    private boolean mousePressed;
    private boolean mouseReleased;
    private boolean rightMouseClicked;
    private boolean rightMousePressed;
    private boolean rightMouseReleased;
    private boolean wheelMouseClicked;
    private boolean wheelMousePressed;
    private boolean wheelMouseReleased;

    private final boolean[] currentlyPressed;
    private final boolean[] pressed;


    public Input() {
        pressed = new boolean[1000];
        currentlyPressed = new boolean[1000];
        mousePosition = new Position(-1, -1);
    }

    public boolean isPressed(int keyCode) {
        if(!pressed[keyCode] && currentlyPressed[keyCode]) {
            pressed[keyCode] = true;
            return true;
        }

        return false;
    }

    public boolean isCurrentlyPressed(int keyCode) {
        return currentlyPressed[keyCode];
    }

    public void cleanUpInputEvents() {
        mouseClicked = false;
        rightMouseClicked = false;
        wheelMouseClicked = false;

        mouseReleased = false;
        rightMouseReleased = false;
        wheelMouseReleased = false;


    }

    public Position getMousePosition() {
        return mousePosition;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public boolean isRightMouseClicked() {
        return rightMouseClicked;
    }

    public boolean isRightMousePressed() {
        return rightMousePressed;
    }

    public boolean isWheelMouseClicked() {
        return wheelMouseClicked;
    }

    public boolean isWheelMousePressed() {
        return wheelMousePressed;
    }

    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public boolean isRightMouseReleased() {
        return rightMouseReleased;
    }

    public boolean isWheelMouseReleased() {
        return wheelMouseReleased;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = false;
        pressed[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = e.getButton() == MouseEvent.BUTTON1;
        rightMousePressed = e.getButton() == MouseEvent.BUTTON3;
        wheelMousePressed = e.getButton() == MouseEvent.BUTTON2;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouseClicked = true;
            mousePressed = false;
            mouseReleased = true;
        }
        if(e.getButton() == MouseEvent.BUTTON2) {
            wheelMouseClicked = true;
            wheelMousePressed = false;
            wheelMouseReleased = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightMouseClicked = true;
            rightMousePressed = false;
            rightMouseReleased = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());
    }

}
