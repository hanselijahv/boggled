package net.team6.boggled.common.core;

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
