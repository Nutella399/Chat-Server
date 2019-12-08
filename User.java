import java.net.*; 
import java.io.*; 

class User implements Runnable{
	
	private Socket socket; 
	private ObjectOutputStream outToClient; 
	private ObjectInputStream inFromClient; 
	private Message message;  
	private Message newMessage;   
	private boolean messageReady; 
	
	public User(Socket socket) {
		this.socket = socket; 
		message = new Message(); 
		newMessage = new Message();
		messageReady = false; 
		try{
			outToClient = new ObjectOutputStream(socket.getOutputStream());  
			inFromClient = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}	
	}

	public void sendMessage(int msgType) {
		message.setMsgType(msgType);
		message.setMsg(); 
		try{
			outToClient.writeObject(message); 
		}catch(Exception e) {
			System.out.println(e.getMessage());	
		}
	}
	
	public void sendMessage(int msgType, String userMessage) {
		message.setMsgType(msgType);
		message.setMsg(userMessage); 
		try{
			outToClient.reset(); 
			outToClient.writeObject(message); 
		}catch(Exception e) {
			System.out.println(e.getMessage());	
		}
	}
	
	@Override	
	public void run() {
		while(true) {
			if(retrieveMessage()){
				messageReady = true; 
			} 	
		}
	}
	
	public boolean getMessageReady() {
		return messageReady; 
	}
	
	public Message getMessage() {
		messageReady = false; 
		return newMessage;
	}
	
	public Message getMessage(int i){
		try{
			Object o = inFromClient.readObject();	
		  newMessage = (Message) o; 
		}catch(Exception e) {
			System.out.println(e.getMessage());	
		}
		return newMessage; 
	}
	
	private boolean retrieveMessage() {
		try{
			Object o = inFromClient.readObject();	
		  newMessage = (Message) o; 
		  return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());	
		}
		return false; 
	}
	
	public Socket getSocket() {
		return socket; 
	}
}
