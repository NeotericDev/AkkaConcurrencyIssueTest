package com.test.actortest.clients;

import com.test.actortest.OpType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.BiFunction;

public class DummyClient {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static final Map<OpType, BiFunction<Integer, Integer, Integer>> OPERATIONS;
    public static final Map<OpType, String> SYMBOLS;

    static {
        OPERATIONS = new HashMap<>();
        OPERATIONS.put(OpType.SUM, Integer::sum);
        OPERATIONS.put(OpType.SUB, (a, b) -> a - b);
        OPERATIONS.put(OpType.MUL, (a, b) -> a * b);
        OPERATIONS.put(OpType.DIV, (a, b) -> a / b);

        SYMBOLS = new HashMap<>();
        SYMBOLS.put(OpType.SUM, " + ");
        SYMBOLS.put(OpType.SUB, " - ");
        SYMBOLS.put(OpType.MUL, " x ");
        SYMBOLS.put(OpType.DIV, " / ");
    }

    public CompletionStage<BiFunction<Integer, Integer, Integer>> getOperationFromExternalService(OpType opType){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return OPERATIONS.get(opType);
        }, executorService);
    }
}
