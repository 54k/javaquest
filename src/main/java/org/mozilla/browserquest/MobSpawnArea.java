package org.mozilla.browserquest;

import org.mozilla.browserquest.model.BQMob;
import org.mozilla.browserquest.world.WorldInstance;

import java.util.concurrent.atomic.AtomicInteger;

public class MobSpawnArea extends SpawnArea {

    private static final AtomicInteger sequence = new AtomicInteger();

    private int nb;
    private String kind;

    public MobSpawnArea(int id, int x, int y, int width, int height, WorldInstance worldInstance, int nb, String kind) {
        super(id, x, y, width, height, worldInstance);
        this.nb = nb;
        this.kind = kind;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void spawnMobs() {
        for (int i = 0; i < nb; i++) {
            Position position = getRandomPositionInsideArea();
            getWorldInstance().spawnEntity(new BQMob(sequence.decrementAndGet(), getKind(), position.getX(), position.getY()));
        }
    }
}
