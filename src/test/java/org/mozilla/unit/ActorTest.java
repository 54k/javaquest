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
import org.mozilla.browserquest.space.AppSpaceClient;
import org.mozilla.browserquest.space.IAppSpaceEventListener;
import org.mozilla.browserquest.space.IAppSpace;
import org.mozilla.browserquest.space.IAppSpaceClient;

import java.util.HashSet;
import java.util.Set;

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
        TestActor spaceActor = factory.newActor(TestActor.class, "test");
        IAppSpace<TestActor> appSpace = new AppSpace<>(spaceActor);
        for (int i = 0; i < 5; i++) {
            IAppSpaceClient appSpaceClient = new AppSpaceClient();
            appSpace.register(appSpaceClient);
        }
        Thread.sleep(500);
        appSpace.destroy();
        Thread.sleep(500);
    }

    @ActorPrototype(AppSpaceComponent.class)
    public abstract static class TestActor extends Actor {

        private String value;

        public TestActor(String value) {
            this.value = value;
        }
    }

    @ComponentPrototype(IAppSpaceEventListener.class)
    public static class AppSpaceComponent extends Component implements IAppSpaceEventListener {

        private int ticks;
        private IAppSpace appSpace;

        private Set<IAppSpaceClient> appSpaceClients = new HashSet<>();

        @Override
        public void onAppSpaceCreated(IAppSpace appSpace) {
            System.out.println("Created: " + appSpace);
            this.appSpace = appSpace;
        }

        @Override
        public void onAppSpaceDestroyed(IAppSpace appSpace) {
            System.out.println("Destroyed: " + appSpace);
            this.appSpace = null;
        }

        @Override
        public void onAppSpaceTick(long tickDeltaTime) {
            ticks++;
            System.out.println("tickDelta: " + tickDeltaTime);
            System.out.println("ticks: " + ticks + ", appSpace: " + appSpace);
        }

        @Override
        public void onAppSpaceClientRegistered(IAppSpaceClient appSpaceClient) {
            System.out.println("Hello: " + appSpaceClient);
            appSpaceClients.add(appSpaceClient);
        }

        @Override
        public void onAppSpaceClientUnregistered(IAppSpaceClient appSpaceClient) {
            System.out.println("Bye: " + appSpaceClient);
            appSpaceClients.remove(appSpaceClient);
        }
    }
}
