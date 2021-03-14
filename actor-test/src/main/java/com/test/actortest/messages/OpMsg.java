package com.test.actortest.messages;

import com.test.actortest.OpType;

import java.io.Serializable;

public class OpMsg implements Serializable {

    private static final long serialVersionUID = 5817648842526628903L;

    private final int a;
    private final int b;
    private final OpType operation;

    public OpMsg(int a, int b, OpType operation) {
        this.a = a;
        this.b = b;
        this.operation = operation;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public OpType getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "OpMsg{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
