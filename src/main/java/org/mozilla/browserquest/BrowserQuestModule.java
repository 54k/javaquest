package org.mozilla.browserquest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import org.mozilla.browserquest.data.DefaultStaticDataService;
import org.mozilla.browserquest.data.StaticDataService;
import org.mozilla.browserquest.idfactory.DefaultIdFactoryService;
import org.mozilla.browserquest.idfactory.IdFactoryService;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.script.DefaultScriptService;
import org.mozilla.browserquest.script.ScriptService;
import org.mozilla.browserquest.template.BQWorldTemplate;
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
        bind(ScriptService.class).to(DefaultScriptService.class).asEagerSingleton();
        bind(StaticDataService.class).to(DefaultStaticDataService.class).asEagerSingleton();
        bind(IdFactoryService.class).to(DefaultIdFactoryService.class).in(Scopes.SINGLETON);

        bind(BQWorld.class).asEagerSingleton();
    }

    @Provides
    private BQWorldTemplate worldMapTemplate(StaticDataService staticDataService) {
        return staticDataService.getWorldTemplate();
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
