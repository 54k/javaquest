package org.mozilla.browserquest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import org.mozilla.browserquest.actor.ActorFactory;
import org.mozilla.browserquest.actor.DefaultActorFactory;
import org.mozilla.browserquest.chat.ChatHandler;
import org.mozilla.browserquest.chat.DefaultChatHandler;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.service.DataService;
import org.mozilla.browserquest.service.DefaultDataService;
import org.mozilla.browserquest.service.DefaultIdFactory;
import org.mozilla.browserquest.service.DefaultScriptService;
import org.mozilla.browserquest.service.DefaultSpawnService;
import org.mozilla.browserquest.service.IdFactory;
import org.mozilla.browserquest.service.ScriptService;
import org.mozilla.browserquest.service.SpawnService;
import org.mozilla.browserquest.template.BQWorldTemplate;
import org.mozilla.browserquest.template.RoamingAreaTemplate;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;

import java.util.List;

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
        bind(DataService.class).to(DefaultDataService.class).asEagerSingleton();

        bind(ChatHandler.class).to(DefaultChatHandler.class).asEagerSingleton();

        bind(IdFactory.class).to(DefaultIdFactory.class).in(Scopes.SINGLETON);
        bind(ActorFactory.class).to(DefaultActorFactory.class).in(Scopes.SINGLETON);

        bind(BQWorld.class).in(Scopes.SINGLETON);
        bind(SpawnService.class).to(DefaultSpawnService.class).in(Scopes.SINGLETON);
    }

    @Provides
    private BQWorldTemplate getWorldMapTemplate(DataService dataService) {
        return dataService.getWorldTemplate();
    }

    @Provides
    private List<RoamingAreaTemplate> getRoamingAreaTemplates(DataService dataService) {
        return dataService.getWorldTemplate().getRoamingAreas();
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
