import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*; 

//has to keep track of the time as well
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
	
	
	public static void main(String[] args) throws IOException {
    	Client client = new Client(); 
   }
   
	public void connect(){
		try {
			clientSocket = new Socket("127.0.0.1", 5000);
			inFromServer = new ObjectInputStream(clientSocket.getInputStream());
			message = (Message) inFromServer.readObject();
			System.out.println("From server: " + message.getMsg());
			if(message.getMsgType() == 1){
				outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
	            System.out.println(users.toString());
	            message.setMsgType(2);
	            outToServer.writeObject(message);
			}	
           while(true){
        	   message = (Message) inFromServer.readObject();
        	   System.out.println(users.toString());
        	   System.out.println("From server: " + message.getMsg());
        	   if(message.getMsgType() != 3) {
        		   break;
        	   }
               message.setMsgType(4);
        	   message.setMsg();
               outToServer.writeObject(message);                     	   
           }
       		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
