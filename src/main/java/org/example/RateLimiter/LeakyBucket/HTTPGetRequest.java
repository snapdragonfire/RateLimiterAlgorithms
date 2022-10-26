package org.example.RateLimiter.LeakyBucket;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HTTPGetRequest {
    public static CompletableFuture<HttpResponse<String>> getAsyncHttp(final String url) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        return HttpClient.newBuilder().build().sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public static boolean makeHttpRequest(String url) throws InterruptedException, ExecutionException, URISyntaxException {
        Future<HttpResponse<String>> response = getAsyncHttp(url);
        while (!response.isDone()) {
            System.out.println("Still waiting..."+Thread.currentThread().getName()+ " Current time:: "+System.currentTimeMillis());
            Thread.sleep(10);
        }
        if(response.isDone()){
            System.out.println(Thread.currentThread().getName()+"::::::::"+response.get().body());
            return true;
        }
        return false;
    }
}
