package tw.com.wd.mq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


public class SimpleMQWorker implements MQWorker {
    @Override
    public Void call() throws Exception {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket zSocket = context.createSocket(SocketType.REQ);
            zSocket.setSendTimeOut(3000);
            zSocket.setReceiveTimeOut(3000);
            zSocket.connect("tcp://*:5555");
            zSocket.send("Hello");
            byte[] rtn = zSocket.recv();

            if (rtn != null) {
                System.out.println("Receive: " + new String(rtn));
            } else {
                System.out.println("Receive: null");
            }

            zSocket.close();
            System.out.printf("Done\n");
        }
        return null;
    }
}
