package org.mozilla.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.actor.JavassistActorFactory;
import org.mozilla.browserquest.space.AppSpace;

public class ActorTest extends Assert {

    private JavassistActorFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new JavassistActorFactory();
    }

    @Test
    public void testCreationWithConstructorParams() {
        TestActor testActor = factory.newActor(TestActor.class, "test");
        assertEquals(testActor.value, "test");
    }

    @Test
    public void testAppSpace() {
        AppSpace<TestActor> appSpace = new AppSpace<>(factory.newActor(TestActor.class, "test"));
    }

    @ActorPrototype
    public abstract static class TestActor extends Actor {

        private String value;

        public TestActor(String value) {
            this.value = value;
        }
    }
}
