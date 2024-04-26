package net.team6.boggled.client.core;

public class Value<DataType> {

    private DataType value;

    public Value(DataType value) {
        this.value = value;
    }

    public DataType get() {
        return value;
    }

    public void setValue(DataType value) {
        this.value = value;
    }
}
