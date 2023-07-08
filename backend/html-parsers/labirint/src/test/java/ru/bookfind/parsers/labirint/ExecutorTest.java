package ru.bookfind.parsers.labirint;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
        writer.println("The first line");
        writer.println("The second line");
        writer.close();

        //        ExecutorService executorService = Executors.newFixedThreadPool(4);
        //        long start = System.nanoTime();
        //        try {
        //            for (int i = 0; i < 100; i++) {
        //                int finalI = i;
        //                //test();
        //                executorService.execute(() -> {
        //                    try {
        //                        test();
        //                    } catch (InterruptedException e) {
        //                        throw new RuntimeException(e);
        //                    }
        //                });
        //            }
        //        } catch (Exception e) {
        //
        //        } finally {
        //            executorService.shutdown();
        //            System.out.println("time = " + (System.nanoTime() - start));
        //        }
    }

    private static void test() throws InterruptedException {
        Thread.sleep(1000);
    }
}
