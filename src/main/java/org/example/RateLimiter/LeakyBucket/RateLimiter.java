package org.example.RateLimiter.LeakyBucket;

import java.util.concurrent.Future;

public interface RateLimiter {

    boolean grantAccess(int id);

}
