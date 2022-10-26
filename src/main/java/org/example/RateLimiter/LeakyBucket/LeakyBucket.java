package org.example.RateLimiter.LeakyBucket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LeakyBucket implements RateLimiter{

    BlockingQueue<Integer> queue;

    public LeakyBucket(int capacity){
        queue = new LinkedBlockingQueue<>(capacity);
    }

    @Override
    public boolean grantAccess(int reqId) {
        if(queue.remainingCapacity()>0){
            queue.add(reqId);
            return true;
        }
        return false;
    }

    public void onRequestCompletion(int reqId) {
        queue.remove(reqId);
    }


}
