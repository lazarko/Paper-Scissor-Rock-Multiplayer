package Client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 *
 * @author Lazarko
 */
public class ServerConnection {

    private BufferedReader fromServer;
    private PrintWriter toServer;
    private Socket socket;
    private final int PORT_NUMBER = 9393;


    public void connect(BroadcastHandler handler) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", PORT_NUMBER));
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new BroadcastReceiver(handler)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect() {
        try {
            toServer.println("QUIT");
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        toServer.println(msg);
    }

    class BroadcastReceiver implements Runnable {
        private final BroadcastHandler handler;
        
        private BroadcastReceiver(BroadcastHandler handler){
            this.handler = handler;
        }
        @Override
        public void run() {
            try {
                while (true) {
                    
                    handler.receiveMsg(fromServer.readLine());
                   
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
