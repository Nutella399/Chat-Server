import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class UI implements ActionListener{
	
	private Message message = new Message();
	private Socket clientSocket = null;
	private ObjectOutputStream outToServer = null;
	String str ="";

	private ObjectInputStream inFromServer = null;
	Scanner scan = new Scanner(System.in);
    JLabel enter, chatroom, title, username;
    JTextField message2, name;
    String naam;
    JButton send, enter_name;
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
        enter.setBounds(465,500, 150,30);
        username=new JLabel("Enter Username:");
        username.setFont(new Font("Monospaced", Font.BOLD, 14));
        username.setForeground(Color.white);
        username.setBounds(10,500, 150,30);
        message2=new JTextField();
        message2.setBounds(590,500, 500,30);
        name=new JTextField();
        name.setBounds(150,500, 150,30);
        send=new JButton("Send");
        send.setBounds(1100,500,70,30);
        send.addActionListener(this);
        enter_name =new JButton("Enter");
        enter_name.setBounds(310,500,70,30);
        enter_name.addActionListener(this);
        chatroom=new JLabel("Chatroom:");
        chatroom.setFont(new Font("Monospaced", Font.BOLD, 14));
        chatroom.setForeground(Color.white);
        chatroom.setBounds(10,130, 100,30);
        frame.add(enter_name);
        frame.add(name);
        frame.add(username);
        frame.add(list2);
        frame.add(title);
        frame.add(chatroom);
        frame.add(enter);
        frame.add(message2);
        frame.add(send);
        frame.setSize(1200,600);
        frame.setLayout(null);
        frame.setVisible(true);
        try {
			clientSocket = new Socket("127.0.0.1", 5000);
			inFromServer = new ObjectInputStream(clientSocket.getInputStream());
			outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			message = (Message) inFromServer.readObject();
        }
        catch (Exception e) {
    		e.printStackTrace();
    	}
        
    }

    public void actionPerformed(ActionEvent e) {
    	str = message2.getText();
        naam = name.getText();
        if(e.getSource() == send) {
            if ((str.compareTo(exit)) == 0) {
                (frame).dispose();
            } else {
                AddChat(naam, str);
                try {
					run();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            message2.setText("");
 
        }
        else{
            AddUsers(naam);
            message2.setText("");
            //name.setEditable(false);
           try {
			getUserName();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        }
    }
    public void getUserName() throws IOException
    {
 
		if(message.getMsgType() == 1){		
		
            message.setUserName(naam);
            message.setMsgType(5);
            message.setMsg();
            outToServer.writeObject(message);
		}	
	   System.out.println("From server: " + message.getMsg());
  
    	
    }
    public void run() throws IOException
	{
    	while(str != null) {
				   //message = (Message) inFromServer.readObject();
				   message.setMsgType(4);
	        	   if(str.compareTo(".")== 0) {
	        		   message.setMsgType(3);
	        		   message.setMsg();
	        		   outToServer.writeObject(message);
	        		   message.setMsgType(6);
	        		   message.setMsg();
	        		   outToServer.writeObject(message);
	        		   System.out.println("From server: " + message.getMsg());
	        		   break;
	        	   }
	        	   else if(message.getMsgType() == 3 || message.getMsgType() == 0)
	        	   {
	        		   message.setMsgType(6);
	        		   message.setMsg();
	        		   outToServer.writeObject(message);
	        		   System.out.println("From server: " + message.getMsg());
	        		   break;
	        	   }
	        	   else if(message.getMsgType() == 4)
	        	   {
	        		  chat.addElement(naam + ":  " + str);
	        		   message.setMsgType(2);
	        		   message.setMsg();
	        		   outToServer.writeObject(message);
	        	   }
	        	   else if(message.getMsgType() == 2)
	        	   {
	        		   System.out.println(message.getMsg());
	        	   }   
				}
	}
		

    public static void main(String[] args) {
        UI userInterface = new UI();

        /*String message = "Hello";
        String user = "David";
        userInterface.AddChat(user, message);*/
    }
}
