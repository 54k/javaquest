package org.mozilla.browserquest.idfactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultIdFactoryService implements IdFactoryService {

    private AtomicInteger sequence = new AtomicInteger();
    private Queue<Integer> queue = new ConcurrentLinkedQueue<>();

    @Override
    public int getNextId() {
        Integer nextId = queue.poll();
        if (nextId == null) {
            nextId = sequence.incrementAndGet();
        }
        return nextId;
    }

    @Override
    public void releaseId(int id) {
        queue.add(id);
    }
}
