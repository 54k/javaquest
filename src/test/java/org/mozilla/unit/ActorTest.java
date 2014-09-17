package org.mozilla.unit;

import io.gwynt.concurrent.DefaultEventExecutor;
import io.gwynt.concurrent.EventExecutor;
import io.gwynt.concurrent.ProcessStatus;
import org.junit.Assert;
import org.junit.Test;

public class ActorTest extends Assert {

    @Test
    public void testSimpleActors() throws Exception {
        for (int i = 0; i < 2; i++) {
            EventExecutor executor = new DefaultEventExecutor();
            ExtendedTestActor testActor = Actor.newActor(ExtendedTestActor.class);
            executor.submitProcess(() -> {
                testActor.asTestBehavior1().test();
                return ProcessStatus.COMPLETED;
            }).sync();
        }
    }

    public interface TestActorProjection {
        @Actor.Projection
        TestBehavior1 asTestBehavior1();
    }

    @Actor.Prototype(DefaultTestBehavior1.class)
    public static abstract class TestActor extends Actor implements TestActorProjection {
    }

    public interface ExtendedTestActorProjection {
        @Actor.Projection
        TestBehavior2 asTestBehavior2();
    }

    @Actor.Prototype(DefaultTestBehavior2.class)
    public static abstract class ExtendedTestActor extends TestActor implements ExtendedTestActorProjection {
    }

    public interface TestBehavior1 {
        void test();

        void validate();
    }

    @Behavior.Prototype(TestBehavior1.class)
    public static class DefaultTestBehavior1 extends Behavior<ExtendedTestActor> implements TestBehavior1 {
        @Override
        public void test() {
            getActor().notify(TestEventListener.class).onTestEvent();
        }

        @Override
        public void validate() {
            assertSame(getActor().asBehavior(TestBehavior1.class), this);
            assertNotNull(getActor().asTestBehavior2());
            getActor().removeBehavior(TestBehavior2.class);
            assertFalse(getActor().hasBehavior(TestBehavior2.class));
            assertNull(getActor().asTestBehavior2());
            getActor().removeBehavior(TestBehavior1.class);
            assertNull(getActor());
        }
    }

    public interface TestBehavior2 {
    }

    @Behavior.Prototype(TestBehavior2.class)
    public static class DefaultTestBehavior2 extends Behavior<TestActor> implements TestBehavior2, TestEventListener {
        @Override
        public void onTestEvent() {
            getActor().asTestBehavior1().validate();
        }
    }

    @Actor.Listener
    public static interface TestEventListener {
        void onTestEvent();
    }
}
