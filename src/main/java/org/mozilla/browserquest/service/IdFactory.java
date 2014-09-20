package org.mozilla.browserquest.service;

public interface IdFactory {

    int getNextId();

    void releaseId(int id);
}
