package org.mozilla.browserquest.idfactory;

public interface IdFactoryService {

    int getNextId();

    void releaseId(int id);
}
