package org.mozilla.browserquest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import org.mozilla.browserquest.gameserver.model.World;
import org.mozilla.browserquest.gameserver.service.MapRegionServiceImpl;
import org.mozilla.browserquest.gameserver.service.SpawnServiceImpl;
import org.mozilla.browserquest.gameserver.service.MapRegionService;
import org.mozilla.browserquest.gameserver.service.SpawnService;
import org.mozilla.browserquest.inject.LazyInjectAspect;
import org.mozilla.browserquest.service.ChatHandler;
import org.mozilla.browserquest.service.ChatHandlerImpl;
import org.mozilla.browserquest.service.DataService;
import org.mozilla.browserquest.service.DataServiceImpl;
import org.mozilla.browserquest.service.IdFactoryImpl;
import org.mozilla.browserquest.service.ObjectFactoryImpl;
import org.mozilla.browserquest.service.ScriptServiceImpl;
import org.mozilla.browserquest.service.IdFactory;
import org.mozilla.browserquest.service.ObjectFactory;
import org.mozilla.browserquest.service.ScriptService;
import org.mozilla.browserquest.template.RoamingAreaTemplate;
import org.mozilla.browserquest.template.WorldTemplate;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.file.FileSystem;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;

import java.util.List;

import static org.aspectj.lang.Aspects.aspectOf;

public class BrowserQuestModule extends AbstractModule {

    private Vertx vertx;
    private Container container;

    public BrowserQuestModule(Vertx vertx, Container container) {
        this.vertx = vertx;
        this.container = container;
    }

    @Override
    protected void configure() {
        requestInjection(aspectOf(LazyInjectAspect.class));

        bind(ScriptService.class).to(ScriptServiceImpl.class).asEagerSingleton();
        bind(DataService.class).to(DataServiceImpl.class).asEagerSingleton();
        bind(ChatHandler.class).to(ChatHandlerImpl.class).asEagerSingleton();

        bind(IdFactory.class).to(IdFactoryImpl.class).in(Scopes.SINGLETON);
        bind(ObjectFactory.class).to(ObjectFactoryImpl.class).in(Scopes.SINGLETON);

        bind(World.class).in(Scopes.SINGLETON);
        bind(SpawnService.class).to(SpawnServiceImpl.class).in(Scopes.SINGLETON);
        bind(MapRegionService.class).to(MapRegionServiceImpl.class).in(Scopes.SINGLETON);
    }

    @Provides
    private WorldTemplate getWorldMapTemplate(DataService dataService) {
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
