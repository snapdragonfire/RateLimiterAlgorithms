package org.example.RateLimiter.LeakyBucket;

import java.util.Random;

public class GenerateIdHelper {
    public int generateIntegerId(int numberOfDigits){
        int digit = 0;
        for(int i=0; i<numberOfDigits; i++){
            digit = digit*10+9;
        }
        Random rnd = new Random();
        return rnd.nextInt(digit);
    }
}
