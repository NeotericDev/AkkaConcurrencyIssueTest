package com.test.actortest.messages;

import java.io.Serializable;

public class OpResult implements Serializable {

    private static final long serialVersionUID = 5817648842526628903L;

    private final int a;
    private final int b;
    private final int result;

    public OpResult(int a, int b, int result) {
        this.a = a;
        this.b = b;
        this.result = result;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "OpResult{" +
                "a=" + a +
                ", b=" + b +
                ", result=" + result +
                '}';
    }
}
