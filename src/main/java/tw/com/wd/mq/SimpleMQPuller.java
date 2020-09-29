package tw.com.wd.mq;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;


public class SimpleMQPuller implements MQWorker {
    private List<String> textList;

    public SimpleMQPuller() {
        super();
        this.textList = new ArrayList<String>();
    }

    @Override
    public Void call() throws Exception {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.PULL);
            socket.connect("tcp://*:5555");
            socket.setReceiveTimeOut(3000);
            System.out.println("Puller ready.");
            int cnt = 0;

            while (true) {
                String data = socket.recvStr();

                if (data == null) {
                    cnt++;

                    if (cnt == 3) {
                        System.out.printf("Pull Done.\n");
                        break;
                    } else {
                        System.out.printf("Sleep...\n");
                        Thread.sleep(1000L);
                    }
                } else {
                    this.textList.add(data);
                }
                System.out.printf("Pull %d elements\n", this.textList.size());
            }

            for (String s : this.textList) {
                System.out.printf("Data: %s\n", s);
            }
        }
        return null;
    }
}
