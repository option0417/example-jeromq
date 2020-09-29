package tw.com.wd.mq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


public class SimpleMQPusher implements MQWorker {
    @Override
    public Void call() throws Exception {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.PUSH);
            socket.bind("tcp://*:5555");

            Thread.sleep(3000L);

            for (int cnt = 1; cnt <= 100; cnt++) {
                socket.send("content" + cnt);
            }
        }
        return null;
    }
}
