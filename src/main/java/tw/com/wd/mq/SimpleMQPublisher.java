package tw.com.wd.mq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


public class SimpleMQPublisher implements MQWorker {
    @Override
    public Void call() throws Exception {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.PUB);
            socket.bind("tcp://*:5555");

            Thread.sleep(3000L);

            socket.send("hello", ZMQ.SNDMORE);
            socket.send("content");
            Thread.sleep(1000L);
            socket.send("hello", ZMQ.SNDMORE);
            socket.send("content2");
            Thread.sleep(1000L);
            socket.send("hello", ZMQ.SNDMORE);
            socket.send("content3");
            Thread.sleep(1000L);
            System.out.println("Publish done.");
        }
        return null;
    }
}
