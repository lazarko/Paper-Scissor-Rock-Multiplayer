package Client;


import Common.Logic;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Lazarko
 */
public class Client {

    private final ServerConnection serverConn = new ServerConnection();
    private final List<String> list = new ArrayList<>();
    List<String> msgBox = new ArrayList<>();
    private int SCORE;
    private final static String WRONG_MSG = "Invalid argument. Commands availaible: ";
    private final static String COMMANDS = "Connect, Quit, Rock, Scissor, Paper";

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            boolean write = true;
            while (write) {
                String in = sc.nextLine();
                if (in.equalsIgnoreCase("CONNECT")) {
                    //CONNECT
                    CompletableFuture.runAsync(() -> {
                        serverConn.connect(new Output());
                    });
                } else if (in.equalsIgnoreCase("QUIT")) {
                    //QUIT
                    write = false;
                    serverConn.disconnect();
                } else {
                    if (in.equalsIgnoreCase(Logic.SCISSOR_STR) || in.equalsIgnoreCase(Logic.ROCK_STR)
                            || in.equalsIgnoreCase(Logic.PAPER_STR)) {
                        list.add(in);
                        CompletableFuture.runAsync(() -> serverConn.sendMsg(in));
                    } else {
                        new Thread(() -> System.out.println(WRONG_MSG + "" + COMMANDS)).start();
                        CompletableFuture.runAsync(() -> serverConn.sendMsg(in));
                    }

                }
            }
        }).start();

    }

    private class Output implements BroadcastHandler {

        private String guess;

        @Override
        public void receiveMsg(String msg) {

            if (!list.isEmpty()) {
                for (int i = 0; i < msgBox.size(); i++) {
                    String msgFromBox = msgBox.get(i);
                    if (Logic.PAPER_STR.equalsIgnoreCase(msgFromBox) || Logic.ROCK_STR.equalsIgnoreCase(msgFromBox)
                            || Logic.SCISSOR_STR.equalsIgnoreCase(msgFromBox)) {
                        guess = list.get(0);
                        String result = Logic.check(guess, msgFromBox);
                        setScore(result);
                        //System.out.println(result); 
                    }
                }
                list.clear();
                msgBox.clear();

            } else {
                printMsg(SCORE, "WAITING FOR YOUR GUESS");
                msgBox.add(msg);

            }

        }

        private void setScore(String res) {
            if (res.equalsIgnoreCase(Logic.WIN_MSG)) {
                printMsg(SCORE++, res);
            } else {
                printMsg(SCORE, res);
            }
        }

        private void printMsg(int score, String msg) {
            new Thread(() -> {
                System.out.println("COMMENT: " + msg + "\n SCORE: " + score);
            }).start();
        }
    }

}
