import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;

public class UI implements ActionListener{

    JLabel enter, chatroom, title;
    JTextField message;
    JButton send;
    JFrame frame;
    DefaultListModel<String> users = new DefaultListModel<>();
    JList<String> list = new JList<>(users);
    DefaultListModel<String> chat = new DefaultListModel<>();
    JList<String> list2 = new JList<>(chat);
    String exit = ".";

    public void AddUsers(String user){
        users.addElement(user);
        list.setBounds(10,160, 100,300);
        frame.add(list);
        frame.repaint();
    }

    public void AddChat( String user, String message){
        LocalTime t = LocalTime.now();
        String time = t.toString();
        chat.addElement(time.substring(0,5) +  "      " + user + ":  " + message);
        list2.setBounds(150,75, 1000,400);
    }

    public UI(){
        frame = new JFrame("Echo to Everyone");
        frame.getContentPane().setBackground(Color.darkGray);
        title=new JLabel("Echo To Everyone");
        title.setFont(new Font("Monospaced", Font.BOLD|Font.ITALIC,30));
        title.setForeground(Color.white);
        title.setBounds(425,20, 500,30);
        enter=new JLabel("Enter Message:");
        enter.setFont(new Font("Monospaced", Font.BOLD, 14));
        enter.setForeground(Color.white);
        enter.setBounds(225,500, 150,30);
        message=new JTextField();
        message.setBounds(350,500, 500,30);
        send=new JButton("Send");
        send.setBounds(860,500,70,30);
        send.addActionListener(this);
        chatroom=new JLabel("Chatroom:");
        chatroom.setFont(new Font("Monospaced", Font.BOLD, 14));
        chatroom.setForeground(Color.white);
        chatroom.setBounds(10,130, 100,30);
        frame.add(list2);
        frame.add(title);
        frame.add(chatroom);
        frame.add(enter);
        frame.add(message);
        frame.add(send);
        frame.setSize(1200,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = message.getText();
        if((s.compareTo(exit)) == 0){
            (frame).dispose();
        }
        else{
            AddChat("User", s);
        }
        message.setText("");
    }

    public static void main(String[] args) {
        UI userInterface = new UI();
        String u = "Korah";
        String v = "Dr.Husain";
        userInterface.AddUsers(u);
        userInterface.AddUsers(v);
        String message = "Hello";
        String user = "David";
        userInterface.AddChat(user, message);
    }
}
