package com.test.actortest.messages;

import com.test.actortest.OpType;

import java.io.Serializable;

public class InitializeAndCacheOperation implements Serializable {

    private static final long serialVersionUID = 2015831912977684383L;

    private final OpType opType;

    public InitializeAndCacheOperation(OpType opType) {
        this.opType = opType;
    }

    public OpType getOpType() {
        return opType;
    }
}
