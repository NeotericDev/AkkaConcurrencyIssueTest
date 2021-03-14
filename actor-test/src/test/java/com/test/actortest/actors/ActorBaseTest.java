package com.test.actortest.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.testkit.TestKit;
import com.test.actortest.MyUtil;
import com.test.actortest.OpType;
import com.test.actortest.messages.OpMsg;
import com.test.actortest.messages.OpResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.test.actortest.MyUtil.*;
import static com.test.actortest.clients.DummyClient.OPERATIONS;
import static com.test.actortest.clients.DummyClient.SYMBOLS;

public abstract class ActorBaseTest {

    protected static ActorSystem system;
    protected static ActorRef actor;
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    @Test
    public void test_addition_async(){
        PrintFB(BLACK, PURPLE_BG, "Started Test for 'test_addition_async'");
        Runnable r = () -> {
            CompletableFuture<Boolean> f1 = CompletableFuture.supplyAsync(this::testForSum);
            CompletableFuture.allOf(f1).join();
        };
        timedTask(r, "test_addition_async:: ");
        System.out.println("\n================================================================================================================================\n");
    }

    @Test
    public void test_subtraction_async(){
        PrintFB(BLACK, PURPLE_BG, "Started Test for 'test_subtraction_async'");
        Runnable r = () -> {
            CompletableFuture<Boolean> f1 = CompletableFuture.supplyAsync(this::testForSub);
            CompletableFuture.allOf(f1).join();
        };
        timedTask(r, "test_subtraction_async:: ");
        System.out.println("\n================================================================================================================================\n");
    }

    @Test
    public void test_multiplication_async(){
        PrintFB(BLACK, PURPLE_BG, "Started Test for 'test_multiplication_async'");
        Runnable r = () -> {
            CompletableFuture<Boolean> f1 = CompletableFuture.supplyAsync(this::testForMul);
            CompletableFuture.allOf(f1).join();
        };
        timedTask(r, "test_multiplication_async:: ");
        System.out.println("\n================================================================================================================================\n");
    }

    @Test
    public void test_division_async(){
        PrintFB(BLACK, PURPLE_BG, "Started Test for 'test_division_async'");
        Runnable r = () -> {
            CompletableFuture<Boolean> f1 = CompletableFuture.supplyAsync(this::testForDiv);
            CompletableFuture.allOf(f1).join();
        };
        timedTask(r, "test_division_async:: ");
        System.out.println("\n================================================================================================================================\n");
    }

    @Test
    public void test_addition_subtraction_async(){
        PrintFB(BLACK, PURPLE_BG, "Started Test for 'test_addition_subtraction_async'");
        Runnable r = () -> {
            CompletableFuture<Boolean> f1 = CompletableFuture.supplyAsync(this::testForSum);
            CompletableFuture<Boolean> f2 = CompletableFuture.supplyAsync(this::testForSub);
            CompletableFuture.allOf(f1, f2).join();
        };
        timedTask(r, "test_addition_subtraction_async:: ");
        System.out.println("\n================================================================================================================================\n");
    }

    @Test
    public void test_add_sub_mul_async(){
        PrintFB(BLACK, PURPLE_BG, "Started Test for 'test_add_sub_mul_async'");
        Runnable r = () -> {
            CompletableFuture<Boolean> f1 = CompletableFuture.supplyAsync(this::testForSum);
            CompletableFuture<Boolean> f2 = CompletableFuture.supplyAsync(this::testForSub);
            CompletableFuture<Boolean> f3 = CompletableFuture.supplyAsync(this::testForMul);
            CompletableFuture.allOf(f1, f2, f3).join();
        };
        timedTask(r, "test_add_sub_mul_async:: ");
        System.out.println("\n================================================================================================================================\n");
    }

    @Test
    public void test_add_sub_mul_div_async(){
        PrintFB(BLACK, PURPLE_BG, "Started Test for 'test_add_sub_mul_div_async'");
        Runnable r = () -> {
            CompletableFuture<Boolean> f1 = CompletableFuture.supplyAsync(this::testForSum);
            CompletableFuture<Boolean> f2 = CompletableFuture.supplyAsync(this::testForSub);
            CompletableFuture<Boolean> f3 = CompletableFuture.supplyAsync(this::testForMul);
            CompletableFuture<Boolean> f4 = CompletableFuture.supplyAsync(this::testForDiv);
            CompletableFuture.allOf(f1, f2, f3, f4).join();
        };
        timedTask(r, "test_add_sub_mul_div_async:: ");
        System.out.println("\n================================================================================================================================\n");
    }

    private Boolean testForSum() {
        OpType opType = OpType.SUM;
        String operation = "Addition";
        doTheTask(opType, operation);
        return true;
    }

    private Boolean testForMul() {
        OpType opType = OpType.MUL;
        String operation = "Multiply";
        doTheTask(opType, operation);
        return true;
    }

    private Boolean testForDiv() {
        OpType opType = OpType.DIV;
        String operation = "Divide";
        doTheTask(opType, operation);
        return true;
    }

    private Boolean testForSub() {
        OpType opType = OpType.SUB;
        String operation = "Subtract";
        doTheTask(opType, operation);
        return true;
    }

    private void doTheTask( OpType opType, String operation) {
        Runnable r = () -> {
            int def = 10;
            IntStream.rangeClosed(101, 110).forEach((i) -> {
                OpResult result = (OpResult)Patterns.ask(actor, new OpMsg(i, def, opType/*Next Operation*/), java.time.Duration.ofMillis(600)).toCompletableFuture().join();//.thenAccept(resultMsg -> {
                //Assertions.assertEquals(result.getA() - result.getB(), result.getResult());
                boolean isPass = OPERATIONS.get(opType).apply(result.getA(), result.getB()) == result.getResult();
                if(isPass) {
                    PrintF("", constructMsg(GREEN, opType, operation, result, " = ", isPass));
                } else {
                    PrintF("", constructMsg(RED, opType, operation, result, " != ", isPass));
                }
                MyUtil.wait(100);
            });
        };
        timedTask(r, "Test:: " + operation);
    }

    private void timedTask(Runnable runnable, String msgPart) {
        long startTimeinmillis = System.currentTimeMillis();
        runnable.run();
        PrintF("", BOLD + msgPart + ":: Completed in " + (System.currentTimeMillis() - startTimeinmillis) + " millis");
    }

    private String constructMsg(String msgColor, OpType opType, String operation, OpResult result, String qualitySign, boolean isPass) {
        return "[" + passOrFail(isPass) + "]" + msgColor + operation + " :: " + result.getA() + SYMBOLS.get(opType) + result.getB() + qualitySign + result.getResult();
    }

    private String passOrFail(boolean isPass) {
        return (isPass ? GREEN_BG + BLACK + "PASSED" : RED_BG + BLACK + "FAILED") + RESET;
    }

    @AfterEach
    void tearDown() {
        TestKit.shutdownActorSystem(system, Duration.apply(2000, TimeUnit.MILLISECONDS), true);
    }
}