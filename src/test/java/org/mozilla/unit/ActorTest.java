package org.mozilla.unit;

import org.junit.Assert;
import org.junit.Test;
import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.actor.JavassistActorFactory;

public class ActorTest extends Assert {

    @Test
    public void testCreationWithConstructorParams() {
        JavassistActorFactory factory = new JavassistActorFactory();
        TestActor testActor = factory.newActor(TestActor.class, "test");
        assertEquals(testActor.value, "test");
    }

    @ActorPrototype
    public abstract static class TestActor extends Actor {

        private String value;

        public TestActor(String value) {
            this.value = value;
        }
    }
}
