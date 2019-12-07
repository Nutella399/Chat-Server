import java.net.*; 
import java.io.*; 

class User{
	
	private Socket socket; 
	private ObjectOutputStream outToClient; 
	private ObjectInputStream inFromClient; 
	private Message message;  
	
	public User(Socket socket) {
		this.socket = socket; 
		message = new Message(); 
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
			outToClient.writeObject(message); 
		}catch(Exception e) {
			System.out.println(e.getMessage());	
		}
	}
	
	public Message getMessage() {
		try{
		
			Object o = inFromClient.readObject();
			if(o != null) {
				 message = (Message) o; 
			}else {
				System.out.println("There is no message for this user"); 
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());	
		}
		return message; 
	}
	
	public Socket getSocket() {
		return socket; 
	}
}
