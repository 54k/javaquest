package org.mozilla.browserquest.model.collection;

import org.mozilla.browserquest.model.actor.BQPlayer;

public interface BQPlayerContainer extends Iterable<BQPlayer> {

    void add(BQPlayer player);

    void remove(int id);

    void remove(String name);

    BQPlayer get(int id);

    BQPlayer get(String name);

    int size();

    boolean contains(BQPlayer player);

    boolean contains(int id);

    boolean contains(String name);

    void clear();
}
