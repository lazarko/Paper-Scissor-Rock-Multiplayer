package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Lazarko
 */
public class Server {

    private final static int PORT_NUMBER = 9393;
    private ServerSocket ss;
    private final List<Handler> clients = new ArrayList<>();
 

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private void start() {
        try {
            System.out.println("SERVER IS RUNNING...");
            ss = new ServerSocket(PORT_NUMBER);
            while (true) {
                Socket socket = ss.accept();
                startHandler(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startHandler(Socket socket) {
        Handler handler = new Handler(this, socket);
        synchronized (clients) {
            clients.add(handler);
            System.out.println("Player joined. Total number of players: " + clients.size());
        }
        Thread thread = new Thread(handler);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

    }

    public void removeClient(Handler handler) {
        synchronized (clients) {
            clients.remove(handler);
        }
    }

    public void broadcast(String msg) {
        synchronized (clients) {
            System.out.println("BROADCASTING: " + msg);
            clients.forEach((handler) -> handler.sendMsg(msg));
        }
    }

}
