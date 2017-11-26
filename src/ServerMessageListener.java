import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Alex on 22.11.2017.
 */
public class ServerMessageListener implements Runnable {

    private boolean isRun = Boolean.TRUE;
    private final Socket socket;
    private final Set<ServerMessageListenerSubscriber> subscribers;


    public ServerMessageListener(Socket socket) {
        this(socket, Collections.emptySet());
    }

    public ServerMessageListener(Socket socket, ServerMessageListenerSubscriber subscriber) {
        this(socket, Collections.singleton(subscriber));
    }

    public ServerMessageListener(Socket socket, Set<ServerMessageListenerSubscriber> subscribers) {
        this.socket = socket;
        this.subscribers = subscribers;
    }

    public boolean addSubscriber(ServerMessageListenerSubscriber subscriber) {
        return subscribers.add(subscriber);
    }

    public void close() {
        isRun = Boolean.FALSE;
    }

    @Override
    public void run() {
        try(ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
            while (isRun) {
                MessageRequest message = (MessageRequest) reader.readObject();
                subscribers.forEach(subscriber -> subscriber.onMessage(message));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
