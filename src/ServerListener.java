import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Alex on 22.11.2017.
 */
public class ServerListener implements Runnable {

    private final Socket socket;
    private ClientGUI clientGUI;


    public ServerListener(Socket socket, ClientGUI clientGUI) {
        this.socket = socket;
        this.clientGUI = clientGUI;
    }

    @Override
    public void run() {
        try(DataInputStream reader = new DataInputStream(socket.getInputStream())) {
            //clientGUI = new ClientGUI();
            while (true) {
                String message = reader.readUTF();
                clientGUI.setAddTextIncoming(message+"\n");
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
