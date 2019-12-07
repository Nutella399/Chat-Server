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
	private HashMap<String, User> users;

	
	private Socket socket = null; 
	
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
           while(true){
        	   
        	   while(scan.nextLine() != null) {
        	   
	        	   if(scan.nextLine().equals('.')) {
	        		   message.setMsgType(3);
	        	   }
	        	   else if(message.getMsgType() == 4)
	        	   {
	        		   String newMessage = scan.nextLine();
	        		   outToServer.writeObject(newMessage);
	        		   message.setMsgType(2);
	        		   message.setMsg();
	        	   }
	        	   else if(message.getMsgType() == 2)
	        	   {
	        		   System.out.println("From server: " + message.getMsg());
	        	   }
	        	   else if(message.getMsgType() == 3)
	        	   {
	        		   System.out.println("From server: " + message.getMsg());
	        		   message.setMsgType(6);
	        		   message.setMsg();
	        		   break;
	        	   }
        	   }	
               //message.setMsgType(4);
        	   //message.setMsg();
               //outToServer.writeObject(message);                     	   
           }
       		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
