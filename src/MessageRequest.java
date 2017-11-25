import java.io.Serializable;
import java.util.Set;

/**
 * Created by Alex on 25.11.2017.
 */
public class MessageRequest implements Serializable {

    private static final long serialVersionUID = -2007776149140328135L;

    private String username;

    private String text;

    private boolean online;

    private Set<String> users;


    public MessageRequest(String username, String text) {
        this.username = username;
        this.text = text;
        this.online = true;
    }

    public MessageRequest(String username, boolean online) {
        this.username = username;
        this.online = online;
    }

    public MessageRequest() {
        this.online = true;
    }

    public String getText() {
        return text;
    }

    public void setText(String message) {
        this.text = message;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
