package tw.com.wd.mq;

import java.util.concurrent.Callable;


public interface MQWorker extends Callable<Void> {
}
