var World = inject("org.mozilla.browserquest.world.World");
var Logger = inject("org.vertx.java.core.logging.Logger");

exports = {
    run: function () {
//        World.populateWorlds(1, 1000);
//        Logger.info("Worlds created");
        ScriptManager.doSome();
    }
};