package org.mozilla.browserquest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;

public class TestModule extends AbstractModule {

    private Vertx vertx;
    private Container container;

    public TestModule(Vertx vertx, Container container) {
        this.vertx = vertx;
        this.container = container;
    }

    @Override
    protected void configure() {

    }

    @Provides
    private Vertx getVertx() {
        return vertx;
    }

    @Provides
    private Container getContainer() {
        return container;
    }

    @Provides
    private Logger getLogger() {
        return container.logger();
    }
}
