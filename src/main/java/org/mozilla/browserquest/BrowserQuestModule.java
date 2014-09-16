package org.mozilla.browserquest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.mozilla.browserquest.script.DefaultScriptService;
import org.mozilla.browserquest.script.ScriptService;
import org.mozilla.browserquest.world.World;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;

public class BrowserQuestModule extends AbstractModule {

    private Vertx vertx;
    private Container container;

    public BrowserQuestModule(Vertx vertx, Container container) {
        this.vertx = vertx;
        this.container = container;
    }

    @Override
    protected void configure() {
        bind(World.class).asEagerSingleton();
        bind(ScriptService.class).to(DefaultScriptService.class).asEagerSingleton();
    }

    @Provides
    private Vertx vertx() {
        return vertx;
    }

    @Provides
    private FileSystem fileSystem() {
        return vertx.fileSystem();
    }

    @Provides
    private Container container() {
        return container;
    }

    @Provides
    private Logger logger() {
        return container.logger();
    }
}
