import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Alex on 22.11.2017.
 */
public class ClientConnection {

    private static final int PORT = 8080;
    private static final String IP = "127.0.0.1";
    private final Socket socket;
    private final ObjectOutputStream writer;
    private final ServerMessageListener listener;

    public ClientConnection() throws IOException {
        this(Collections.emptySet());
    }

    public ClientConnection(ServerMessageListenerSubscriber subscriber) throws IOException {
        this(Collections.singleton(subscriber));
    }

    public ClientConnection(Set<ServerMessageListenerSubscriber> subscribers) throws IOException {
        this.socket = new Socket(IP, PORT);
        this.writer = new ObjectOutputStream(socket.getOutputStream());
        this.listener = new ServerMessageListener(socket, subscribers);
        Thread thread = new Thread(this.listener);
        thread.setDaemon(true);
        thread.start();
    }

    public void sendMessage(MessageRequest message) throws IOException {
        writer.writeObject(message);
        writer.flush();
    }

    public void close() throws IOException {
        writer.flush();
        writer.close();
        socket.close();
        listener.close();
    }

    public ServerMessageListener getListener() {
        return listener;
    }

}
