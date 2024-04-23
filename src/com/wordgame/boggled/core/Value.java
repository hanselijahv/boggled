package com.wordgame.boggled.core;

public class Value<T> {

    private T value;

    public Value(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
