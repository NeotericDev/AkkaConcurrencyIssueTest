package com.test.actortest.actors;

import akka.actor.Props;
import com.test.actortest.OpType;
import com.test.actortest.clients.DummyClient;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Uses Mutable non-containder-variable to hold the cached data. So it is not suitable for the use-cases where more than one-type of operation in this example.
 * Or in general, if the cached data is not common for all type of Message Requests, then we can run into unintended results
 * */
public class ActorOne extends ActorBase {
    private BiFunction<Integer, Integer, Integer> cachedOperation = null;

    public ActorOne(DummyClient client) {
        super(client);
    }

    @Override
    protected boolean isCacheExpired(OpType opType) {
        return Objects.isNull(cachedOperation);
    }

    @Override
    protected void cacheOperation(OpType opType, BiFunction<Integer, Integer, Integer> operationToCache) {
        this.cachedOperation = operationToCache;
    }

    @Override
    protected void clearCacheOperation(OpType opType) {
        this.cachedOperation = null;
    }

    @Override
    protected BiFunction<Integer, Integer, Integer> getCacheOperation(OpType opType) {
        return cachedOperation;
    }

    public static Props defaultProps() {
        return Props.create(ActorOne.class, new DummyClient());
    }
}
