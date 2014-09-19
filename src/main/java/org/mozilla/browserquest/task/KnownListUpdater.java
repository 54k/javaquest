package org.mozilla.browserquest.task;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.actor.BQObject;
import org.vertx.java.core.Vertx;

public class KnownListUpdater {

    @Inject
    public KnownListUpdater(Vertx vertx, BQWorld world) {
        UpdateKnownListTask task = new UpdateKnownListTask(world);
        vertx.setPeriodic(100, i -> task.run());
    }

    private static class UpdateKnownListTask implements Runnable {

        private BQWorld world;

        private UpdateKnownListTask(BQWorld world) {
            this.world = world;
        }

        @Override
        public void run() {
            world.getRegions().values().stream().filter(BQWorldRegion::isActive).forEach(this::updateRegion);
        }

        private void updateRegion(BQWorldRegion region) {
            for (BQObject object : region.getObjects().values()) {
                object.getKnownList().updateKnownObjects();
            }
        }
    }
}
