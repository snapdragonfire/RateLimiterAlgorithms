package org.example.RateLimiter.TokenBucketAlgorithm;

public class TokenBucket {
    private final long maxBucketSize;
    private final long refillRate;

    private long currentBucketSize;
    private long lastRefillTimeStamp;

    public TokenBucket(long maxBucketSize, long refillRate){
        this.maxBucketSize = maxBucketSize;
        this.refillRate = refillRate;

        currentBucketSize = maxBucketSize;
        lastRefillTimeStamp = System.nanoTime();
    }

    public synchronized boolean allowRequest(int tokens){
        refill();

        if(currentBucketSize>tokens){
            currentBucketSize -= tokens;
            return true;
        }

        return false;
    }

    private void refill(){
        long currentTimeStamp = System.nanoTime();

        double tokensToAdd = (currentTimeStamp-lastRefillTimeStamp)*refillRate/1e9;

        currentBucketSize = (long) Math.min(currentBucketSize+tokensToAdd, maxBucketSize);

        lastRefillTimeStamp = currentTimeStamp;
    }
}
