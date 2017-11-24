import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Alex on 22.11.2017.
 */
public class Client {

    private static final int PORT = 8080;
    private static final String IP = "127.0.0.1";
    private ClientGUI clientGUI;


    public void run() {
        try (
                Socket socket = new Socket(IP, PORT);
                DataOutputStream writer = new DataOutputStream(socket.getOutputStream())
                //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ) {
            //String line;
            clientGUI = new ClientGUI();
            Thread socketThread = new Thread(new ServerListener(socket, clientGUI));
            socketThread.setDaemon(true);
            socketThread.start();
            //while (!(line = bufferedReader.readLine()).equals("exit")) {
            //    writer.writeUTF(line);
            //    writer.flush();
            //}
            while (true) {
                clientGUI.getSendButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (clientGUI.getTextFieldInput().equals("")) {
                            return;
                        }
                        if (!clientGUI.getTextFieldInput().equals("exit")) {
                            try {
                                writer.writeUTF(clientGUI.getTextFieldLogin()+ ": " +
                                        clientGUI.getTextFieldInput());
                                writer.flush();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else
                        {
                            clientGUI.getFrame().dispose();
                            System.exit(0);
                        }
                        clientGUI.getFieldInput().setText("");
                        clientGUI.getFieldInput().requestFocus();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
