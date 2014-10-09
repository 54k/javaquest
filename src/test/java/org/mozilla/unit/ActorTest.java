package org.mozilla.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.actor.JavassistActorFactory;
import org.mozilla.browserquest.space.AppSpace;
import org.mozilla.browserquest.space.AppSpaceEventListener;
import org.mozilla.browserquest.space.IAppSpace;

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
    public void testAppSpace() throws Exception {
        AppSpace<TestActor> appSpace = new AppSpace<>(factory.newActor(TestActor.class, "test"));
        Thread.sleep(10_000);
    }

    @ActorPrototype(AppSpaceComponent.class)
    public abstract static class TestActor extends Actor {

        private String value;

        public TestActor(String value) {
            this.value = value;
        }
    }

    @ComponentPrototype(AppSpaceEventListener.class)
    public static class AppSpaceComponent extends Component implements AppSpaceEventListener {

        private int ticks;
        private IAppSpace appSpace;

        @Override
        public void onAppSpaceCreated(IAppSpace appSpace) {
            this.appSpace = appSpace;
        }

        @Override
        public void onAppSpaceDestroyed(IAppSpace appSpace) {
            this.appSpace = null;
        }

        @Override
        public void onAppSpaceTick(long tickDeltaTime) {
            ticks++;
            System.out.println("tickDelta: " + tickDeltaTime);
            System.out.println("ticks: " + ticks + ", appSpace: " + appSpace);
        }
    }
}
