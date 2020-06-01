package com.fml;

import java.util.concurrent.*;

public class CallableDemo implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("come in ");
        return "SECCES";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CallableDemo callableDemo=new CallableDemo();
        Future<String> future = executorService.submit(callableDemo);
        System.out.println(future.get());
    }
}
