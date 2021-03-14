package com.test.actortest.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;
import com.test.actortest.MyUtil;
import com.test.actortest.OpType;
import com.test.actortest.clients.DummyClient;
import com.test.actortest.messages.InitializeAndCacheOperation;
import com.test.actortest.messages.OpMsg;
import com.test.actortest.messages.OpResult;

import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import static com.test.actortest.MyUtil.PrintF;
import static com.test.actortest.clients.DummyClient.SYMBOLS;

public abstract class ActorBase extends AbstractActor {
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m";
    public static final String WHITE = "\u001B[37m";
    public static final String BLACK_BAG = "\u001B[40m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLUE = "\u001B[34m";


    protected final DummyClient client;

    public ActorBase(DummyClient client) {
        this.client = client;
    }

    protected abstract boolean isCacheExpired(OpType opType);
    protected abstract void cacheOperation(OpType opType, BiFunction<Integer, Integer, Integer> operationToCache);
    protected abstract void clearCacheOperation(OpType opType);
    protected abstract BiFunction<Integer, Integer, Integer> getCacheOperation(OpType opType);


    private void handleMsgAndMaintainCache(OpMsg msg) {
        ActorRef sender = sender();
        ActorRef myself = self();

        PrintF("","Received Operational Msg for '" + msg.getOperation() + "'");

        if(isCacheExpired(msg.getOperation())) {
            PrintF(BLUE,"CachedOperation is null for Operation '" + msg.getOperation() + "', so.." );
            setCacheOperation(msg.getOperation()).thenAccept(op -> {
                PrintF(BLUE,"Updated the CachedOperation with new Operation for '" + msg.getOperation() + "'");
                doOperationAndReply(msg, sender, myself);
            });
        } else {
            //PrintF("","Operating with CachedOperation");
            doOperationAndReply(msg, sender, myself);
        }
    }

    private CompletionStage<BiFunction<Integer, Integer, Integer>> setCacheOperation(OpType opType) {
        ActorSystem system = context().system();
        ActorRef self = self();
        return client.getOperationFromExternalService(opType).thenApply(operation -> {
            cacheOperation(opType, operation);
            system.scheduler().scheduleOnce(java.time.Duration.ofMillis(500), () -> self.tell(opType, self), system.dispatcher());
            return operation;
        });
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(OpType.class, this::clearCachedOperation)
                .match(OpMsg.class, this::handleMsgAndMaintainCache)
                .match(InitializeAndCacheOperation.class, msg -> {
                    setCacheOperation(msg.getOpType()).thenAccept(op -> {
                        MyUtil.PrintFB(YELLOW, "",  "Initialized the First CachedOperation for " + BLACK_BAG + CYAN + "  " +msg.getOpType().name() + "  ");
                    });
                })
                .build();
    }

    private void clearCachedOperation(OpType opType) {
        clearCacheOperation(opType);
        PrintF(BLUE,"CachedOperation is Expired");
    }

    private void doOperationAndReply(OpMsg msg, ActorRef sender, ActorRef myself) {
        int result = getCacheOperation(msg.getOperation()).apply(msg.getA(), msg.getB());
        sender.tell(new OpResult(msg.getA(), msg.getB(), result), myself);
        PrintF("","Returned the Result '" + result + "' for CurrentOperation " + msg.getA() + SYMBOLS.get(msg.getOperation()) + msg.getB());
    }
}
