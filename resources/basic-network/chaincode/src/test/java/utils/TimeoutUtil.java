package utils;

import java.util.concurrent.*;

public class TimeoutUtil {

    public static void runWithTimeout(Runnable callable, long timeout, TimeUnit timeUnit) throws Exception {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                callable.run();
            } finally {
                latch.countDown();
            }
        });
        try {
            executor.execute(t);
            if (!latch.await(timeout, timeUnit)) {
                throw new TimeoutException();
            }
        } finally {
            executor.shutdown();
            t.interrupt();
        }
    }
}

