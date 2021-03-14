package com.test.actortest.actors;

import akka.actor.Props;
import com.test.actortest.OpType;
import com.test.actortest.clients.DummyClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Uses Mutable containder-variable to hold the cached data. So it is suitable for the use-cases where more than one-type of operation in this example.
 * Or in general, even if the cached data is not common for all type of Message Requests, this solution works fine
 * since we can have more than one cachedData for each type of request(i.e operation in this example) in a container like Map
 * */
public class ActorTwo extends ActorBase {

    Map<OpType, BiFunction<Integer, Integer, Integer>> cachedOperations = new ConcurrentHashMap<>();

    public ActorTwo(DummyClient client) {
        super(client);
    }

    @Override
    protected boolean isCacheExpired(OpType opType) {
        return !cachedOperations.containsKey(opType);
    }

    @Override
    protected void cacheOperation(OpType opType, BiFunction<Integer, Integer, Integer> operationToCache) {
        cachedOperations.put(opType, operationToCache);
    }

    @Override
    protected void clearCacheOperation(OpType opType) {
        cachedOperations.remove(opType);
    }

    @Override
    protected BiFunction<Integer, Integer, Integer> getCacheOperation(OpType opType) {
        return cachedOperations.get(opType);
    }

    public static Props defaultProps() {
        return Props.create(ActorTwo.class, new DummyClient());
    }
}
