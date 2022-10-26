package org.example.RateLimiter.LeakyBucket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserBucketCreator {
    Map<Integer, LeakyBucket> bucket;

    HTTPGetRequest httpGetRequest = new HTTPGetRequest();

    public UserBucketCreator(int id){
        bucket = new HashMap<>();
        bucket.put(id, new LeakyBucket(5));
    }

    void accessApplication(int id, String url) throws URISyntaxException, ExecutionException, InterruptedException, IOException {

//        if(bucket.get(id) == null){
//            bucket.put(id, new LeakyBucket(5));
//        }
        int reqId = new GenerateIdHelper().generateIntegerId(5);
        if(bucket.get(id).grantAccess(reqId)){
            System.out.println(Thread.currentThread().getName()+" -> You can access the application");
            boolean status = httpGetRequest.makeHttpRequest(url);
            if(status){
                bucket.get(id).onRequestCompletion(reqId);
            }
        } else {
//            System.out.println(Thread.currentThread().getName()+" ->Too many requests at this moment, please try after some time");
        }
    }
}
