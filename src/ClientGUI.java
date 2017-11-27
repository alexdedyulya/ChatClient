import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Alex on 23.11.2017.
 */
public class ClientGUI implements ServerMessageListenerSubscriber {

    private JTextArea incoming = new JTextArea(27, 30);
    private JTextField fieldInput = new JTextField(30);
    private JTextArea listOfClient = new JTextArea(27, 10);
    private JButton sendButton = new JButton("Send");
    private JFrame frame = new JFrame("Chat client");
    private JTextField fieldLogin = new JTextField(15);
    private JButton enterButton = new JButton("Enter");
    private JFrame loginFrame = new JFrame("Chat client Login");
    private ClientConnection connection;


    public String getTextFieldLogin() {
        return fieldLogin.getText();
    }

    public JTextField getFieldInput() {
        return fieldInput;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public String getTextFieldInput() {
        return fieldInput.getText();
    }

    public String getTextIncoming() {
        return incoming.getText();
    }

    private void addTextIncoming(String username, String message) {
        this.incoming.append(String.format("%s : %s\n", username, message));
    }

    private void addOnline(Set<String> users) {
        this.listOfClient.setText("");
        users.forEach(user -> this.listOfClient.append(String.format("%s\n", user)));
    }

    public String getTextListOfClient() {
        return listOfClient.getText();
    }

    public void setTextListOfClient(String message) {
        this.listOfClient.setText(message);
    }

    public ClientGUI() {
        loginFrame.setBounds(200, 100, 300, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLogin = new GridBagLayout();
        loginFrame.setLayout(gridBagLogin);

        GridBagConstraints gbcLogin = new GridBagConstraints();
        gbcLogin.anchor = GridBagConstraints.NORTH;
        gbcLogin.fill = GridBagConstraints.NONE;
        gbcLogin.gridheight = 1;
        gbcLogin.gridwidth = GridBagConstraints.REMAINDER;
        gbcLogin.gridx = GridBagConstraints.RELATIVE;
        gbcLogin.gridy = GridBagConstraints.RELATIVE;
        gbcLogin.insets = new Insets(40, 0, 5, 0);
        gbcLogin.ipadx = 0;
        gbcLogin.ipady = 0;
        gbcLogin.weightx = 0.0;
        gbcLogin.weighty = 0.0;

        gridBagLogin.setConstraints(fieldLogin, gbcLogin);
        loginFrame.add(fieldLogin);

        gridBagLogin.setConstraints(enterButton, gbcLogin);
        loginFrame.add(enterButton);
        enterButton.addActionListener(new ClientGUI.EnterButtonListener());

        frame.setBounds(200, 100, 550, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridBagLayout gridBag = new GridBagLayout();
        frame.setLayout(gridBag);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = GridBagConstraints.RELATIVE;
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;

        incoming.setEditable(false);
        incoming.setLineWrap(true);
        JScrollPane incomingScroll = new JScrollPane(incoming);
        gridBag.setConstraints(incomingScroll, c);
        frame.add(incomingScroll);


        c.gridy = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(fieldInput, c);
        frame.add(fieldInput);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    connection.sendMessage(new MessageRequest(getTextFieldLogin(), false));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 1;
        gridBag.setConstraints(sendButton, c);
        sendButton.addActionListener(e -> {
            if (getTextFieldInput().equals("")) {
                return;
            }
            if (!getTextFieldInput().equals("exit")) {
                try {
                    connection.sendMessage(new MessageRequest(getTextFieldLogin(), getTextFieldInput()));
                    fieldInput.setText("");
                    fieldInput.requestFocus();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    connection.sendMessage(new MessageRequest(getTextFieldLogin(), false));
                    connection.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });
        frame.add(sendButton);

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 30;
        c.insets = new Insets(5, 0, 5, 5);

        JScrollPane listScroll = new JScrollPane(listOfClient);
        listOfClient.setEditable(false);
        listOfClient.setLineWrap(true);
        gridBag.setConstraints(listScroll, c);
        frame.add(listScroll);

        loginFrame.setVisible(true);
    }

    public class EnterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (fieldLogin.getText().equals("")) {
                return;
            }

            loginFrame.setVisible(false);
            frame.setVisible(true);
            try {
                connection = new ClientConnection(ClientGUI.this);
                connection.sendMessage(new MessageRequest(getTextFieldLogin(), true));
            } catch (IOException e1) {
                System.out.println("asd");
            }
        }

    }

    @Override
    public void onMessage(MessageRequest message) {
        if (message.getText() != null) {
            addTextIncoming(message.getUsername(), message.getText());
        }

        if (message.getUsers() != null) {
            addOnline(message.getUsers());
        }
    }

}
