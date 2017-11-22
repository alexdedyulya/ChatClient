import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Alex on 22.11.2017.
 */
public class ServerListener implements Runnable {

    private final Socket socket;


    public ServerListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(DataInputStream reader = new DataInputStream(socket.getInputStream())) {
            while (true) {
                String message = reader.readUTF();
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
