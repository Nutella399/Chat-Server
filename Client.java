import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*; 

public class Client{
	
	private Message message = new Message();
	private Socket clientSocket = null;
	private ObjectOutputStream outToServer = null;

	private ObjectInputStream inFromServer = null;
	Scanner scan = new Scanner(System.in);
	UI ui;
	//private HashMap<String, User> users;
	//private Socket socket = null; 
	
	public Client() {	
	}
	
	
	public static void main(String[] args) throws IOException{
    	Client client = new Client(); 
    	client.connect();
    }

   
	public void connect(){
		try {
			clientSocket = new Socket("127.0.0.1", 5000);
			inFromServer = new ObjectInputStream(clientSocket.getInputStream());
			outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			message = (Message) inFromServer.readObject();
			System.out.println("From server: " + message.getMsg());
			if(message.getMsgType() == 1){		
				
	            String name = scan.nextLine();
	            message.setUserName(name);
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
       		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
