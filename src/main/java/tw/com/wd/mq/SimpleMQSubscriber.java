package tw.com.wd.mq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


public class SimpleMQSubscriber implements MQWorker {
    @Override
    public Void call() throws Exception {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.SUB);
            socket.connect("tcp://*:5555");
            boolean isSubscribe = socket.subscribe("hello");
            System.out.println("subscribe done and " + isSubscribe);

            while (true) {
                String topic = socket.recvStr();
                System.out.println("[" + this.toString() + "] " + "topic: " + topic);
                if (topic == null)
                    break;
                String data = socket.recvStr();
                assert (topic.equals("hello"));
                System.out.println("[" + this.toString() + "] " + "Data: " + data);
            }
        }
        return null;
    }
}
