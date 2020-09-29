package tw.com.wd.mq;

import java.util.concurrent.*;


public class Executor {
    public static void main(String[] args) throws Exception {
        BlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<>(100);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 1000L, TimeUnit.MILLISECONDS, threadQueue);

        Future f1 = pool.submit(new SimpleMQPusher());
        Thread.sleep(1000L);
        Future f2 = pool.submit(new SimpleMQPuller());
        Future f3 = pool.submit(new SimpleMQPuller());

        while (true) {
            if (f1.isDone() && f2.isDone() && f3.isDone()) {
                break;
            } else {
                Thread.sleep(1000L);
            }
        }
        pool.shutdown();
    }
}
