import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 23.11.2017.
 */
public class ClientGUI {
    private JTextArea incoming = new JTextArea(27,30);
    private JTextField fieldInput = new JTextField(30);
    private JTextArea listOfClient = new JTextArea(27,10);
    private JButton sendButton = new JButton("Send");
    private JFrame frame = new JFrame("Chat client");

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
        clientGUI.go();
    }
    public void go(){

        frame.setBounds(200,100,550, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridBagLayout gridBag = new GridBagLayout();
        frame.setLayout(gridBag);

        GridBagConstraints c =  new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill   = GridBagConstraints.NONE;
        c.gridheight = GridBagConstraints.RELATIVE;
        c.gridwidth  = GridBagConstraints.RELATIVE;
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

        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 1;
        gridBag.setConstraints(sendButton, c);
        frame.add(sendButton);
        //sendButton.addActionListener(new ClientGUI.SendButtonListener());

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 30;
        c.insets = new Insets(5, 0, 5, 5);
        //c.ipady = 20;

        JScrollPane listScroll = new JScrollPane(listOfClient);
        listOfClient.setEditable(false);
        listOfClient.setLineWrap(true);
        gridBag.setConstraints(listScroll, c);
        frame.add(listScroll);

        frame.setVisible(true);

        //frame.addComponentListener(new FrameFocus());
        //frame.addWindowListener(new WindowClose());

    }
}
