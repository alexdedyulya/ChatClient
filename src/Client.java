import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Alex on 22.11.2017.
 */
public class Client {

    private static final int PORT = 8080;
    private static final String IP = "127.0.0.1";


    public void run() {
        try (
                Socket socket = new Socket(IP, PORT);
                DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String line;
            Thread socketThread = new Thread(new ServerListener(socket));
            socketThread.setDaemon(true);
            socketThread.start();
            while (!(line = bufferedReader.readLine()).equals("exit")) {
                writer.writeUTF(line);
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
