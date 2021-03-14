package com.test.actortest.actors;

import akka.actor.ActorSystem;
import com.test.actortest.MyUtil;
import org.junit.jupiter.api.BeforeEach;

class ActorTwoTest  extends ActorBaseTest {

    @BeforeEach
    void setUp() {
        system = ActorSystem.create("MyWorld");
        actor = system.actorOf(ActorTwo.defaultProps());
        //actor.tell(new InitializeAndCacheOperation(OpType.SUM), ActorRef.noSender());
        MyUtil.wait(100);
    }

}