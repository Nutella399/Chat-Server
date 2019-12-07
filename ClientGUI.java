import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientGUI extends Frame implements ActionListener, Runnable

{
	// Declarations
	private Message message = new Message();
	private Socket clientSocket = null;
	private ObjectOutputStream outToServer = null;

	private ObjectInputStream inFromServer = null;
	Scanner scan = new Scanner(System.in);
	Button b;
	TextField tf;

	TextArea ta;

	Socket s;

	PrintWriter pw;

	BufferedReader br;

	Thread th;

	public ClientGUI()

	{

		Frame f = new Frame("Client Side Chatting");// Frame for Client

		f.setLayout(new FlowLayout());// set layout

		f.setBackground(Color.orange);// set background color of the Frame

		b = new Button("Send");// Send Button

		b.addActionListener(this);// Add action listener to send button.

		f.addWindowListener(new W1());// add Window Listener to the Frame

		tf = new TextField(15);

		ta = new TextArea(12, 20);

		ta.setBackground(Color.cyan);

		f.add(tf);// Add TextField to the frame

		f.add(b);// Add send Button to the frame

		f.add(ta);// Add TextArea to the frame

		try {

			s = new Socket(InetAddress.getLocalHost(), 5000);// Socket for client

			// below line reads input from InputStreamReader

			br = new BufferedReader(new InputStreamReader(s.getInputStream()));

			// below line writes output to OutPutStream

			pw = new PrintWriter(s.getOutputStream(), true);

		} catch (Exception e)

		{
		}

		th = new Thread(this);// start a new thread

		th.setDaemon(true);// set the thread as demon

		th.start();

		setFont(new Font("Arial", Font.BOLD, 20));

		f.setSize(200, 200);// set the size

		f.setVisible(true);

		f.setLocation(100, 300);// set the location

		f.validate();

	}

	// method required to close the Frame on clicking "X" icon.

	private class W1 extends WindowAdapter

	{

		public void windowClosing(WindowEvent we)

		{

			System.exit(0);

		}

	}

	// This method will called after clicking on Send button.

	public void actionPerformed(ActionEvent ae)

	{

		pw.println(tf.getText());// write the value of textfield into PrintWriter

		tf.setText("");// clean the textfield

	}

	// Thread running as a process in background

	public void run()

	{
		try {
			clientSocket = new Socket("127.0.0.1", 5000);
			inFromServer = new ObjectInputStream(clientSocket.getInputStream());
			outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			message = (Message) inFromServer.readObject();
			System.out.println("From server: " + message.getMsg());
			if(message.getMsgType() == 1){		
				
	            pw.println(tf.getText());
	            message.setUserName(tf.getText());
	            message.setMsgType(5);
	            message.setMsg();
	            outToServer.writeObject(message);
			}	
		   System.out.println("From server: " + message.getMsg());
		   
           while(true){
        	   while(scan.nextLine() != null) {
	        	   if(scan.nextLine().compareTo(".")== 0) {
	        		   message.setMsgType(3);
	        		   message.setMsg();
	        		   message.setMsgType(6);
	        		   message.setMsg();
	        		   System.out.println("From server: " + message.getMsg());
	        		   break;
	        	   }
	        	   else if(message.getMsgType() == 3 || message.getMsgType() == 0)
	        	   {
	        		   message.setMsgType(6);
	        		   message.setMsg();
	        		   System.out.println("From server: " + message.getMsg());
	        		   break;
	        	   }
	        	   else if(message.getMsgType() == 4)
	        	   {
	        		   String newMessage = scan.nextLine();
	        		   message.setMsg(newMessage);
	        		   System.out.println(message.getMsg());
	        		   message.setMsgType(2);
	        		   message.setMsg();
	        	   }
	        	   else if(message.getMsgType() == 2)
	        	   {
	        		   System.out.println(message.getMsg());
	        	   }
        	   }	                 	   
           }
		}
	 catch (Exception e) {
		e.printStackTrace();
	}
		

	}

	// Main method

	public static void main(String args[])

	{

		// Instantiate AppClient class

		ClientGUI client = new ClientGUI();

	}

}