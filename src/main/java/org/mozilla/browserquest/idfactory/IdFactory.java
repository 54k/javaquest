package org.mozilla.browserquest.idfactory;

public interface IdFactory {

    int getNextId();

    void releaseId(int id);
}
