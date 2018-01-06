package Server;


import Server.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Lazarko
 */
public class Handler implements Runnable {

    private Socket socket;
    private Server server;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private boolean isConnected;
    private String input;

    public Handler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        isConnected = true;
    }

    @Override
    public void run() {
        try {
            fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toClient = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        while (isConnected) {
            try {
                input = fromClient.readLine();
                if (input.equalsIgnoreCase("QUIT")) {
                    disconnect();
                } else {
                    server.broadcast(input);
                }

            } catch (IOException ex) {
                Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            }catch(NullPointerException e){
                System.out.println("Connection with client broken");
            }
        }
    }

    public void sendMsg(String msg) {
        toClient.println(msg);

    }

    private void disconnect() {
        
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Client has disconnected");
        server.removeClient(this);
        isConnected = false;
    }
}
