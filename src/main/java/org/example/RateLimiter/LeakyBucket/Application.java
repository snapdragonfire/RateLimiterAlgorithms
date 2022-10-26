package org.example.RateLimiter.LeakyBucket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Application {
    static String url = "https://reqres.in/api/users";
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        int intId = new GenerateIdHelper().generateIntegerId(6);
        UserBucketCreator userBucketCreator = new UserBucketCreator(intId);
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        AtomicInteger count = new AtomicInteger();


        while(System.currentTimeMillis()-startTime<=6000){
//            System.out.println(startTime+ ".....Start time");

            executorService.execute(() -> {
                try {
                    count.getAndIncrement();
                    userBucketCreator.accessApplication(intId, url);
                } catch (URISyntaxException | ExecutionException | InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            });

//            System.out.println(System.currentTimeMillis()+ ".....Current time");

            if (System.currentTimeMillis()-startTime>=6000) {
                System.out.println("SHUTTING DOWN EXECUTOR SERVICE");
                executorService.shutdown();
                break;
            }


            System.out.println("TOTAL NO OF REQUESTS::"+count);

        }
//        System.out.println(System.currentTimeMillis()+ ".....Current time");

        executorService.shutdown();
    }
}
