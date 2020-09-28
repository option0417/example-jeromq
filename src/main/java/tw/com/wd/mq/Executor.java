package tw.com.wd.mq;

import java.util.concurrent.*;


public class Executor {
    public static void main(String[] args) throws Exception {
        BlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<>(100);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 3, 1000L, TimeUnit.MILLISECONDS, threadQueue);

        pool.submit(new SimpleMQPublisher());
        Thread.sleep(1000L);
        Future future = pool.submit(new SimpleMQSubscriber());
        Thread.currentThread().join();
    }
}
