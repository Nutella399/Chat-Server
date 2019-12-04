import java.net.*; 
import java.io.*; 

class Node{
	
	private Socket socket; 
	private ObjectOutputStream outToClient; 
	private ObjectInputStream inFromClient; 
	private Message message;  
	
	public Node(Socket socket) {
		this.socket = socket; 
		message = new Message(); 
		try{
			outToClient = new ObjectOutputStream(socket.getOutputStream());  
			inFromClient = new ObjectInputStream(socket.getInputStream());  
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}	
	}

/*	
	public Message getMessage() {
		return message; 
	}
	
	public ObjectOutputStream getOutToClient() {
		return outToClient; 
	}
	
	public ObjectInputStream getInFromClient() {
		return inFromClient; 
	}
*/
}
