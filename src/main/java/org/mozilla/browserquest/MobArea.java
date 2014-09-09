package org.mozilla.browserquest;

public class MobArea extends Area {

    private int nb;
    private String kind;

    public MobArea(int id, int x, int y, int width, int height, WorldServer worldServer, int nb, String kind) {
        super(id, x, y, width, height, worldServer);
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

        }
    }
}
