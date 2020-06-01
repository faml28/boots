package com.fml;

public class TestDemo extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(6000);
            System.out.println("come in");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestDemo testDemo=new TestDemo();
        testDemo.start();
        System.out.println("main ");
    }
}
