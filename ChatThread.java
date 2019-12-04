import java.net.*; 
import java.io.*; 
import java.util.*; 

class ChatThread implements Runnable{
	
	private HashMap<String, Node> users; 
	private PriorityQueue<Message> messageQueue;	
	private ObjectOutputStream outToClient; 
	private ObjectInputStream inFromClient;  
	
	public ChatThread() {
		users = new HashMap<>(); 
		messageQueue = new PriorityQueue<>();
	}
	
	@Override	
	public void run() {
		Message message = messageQueue.poll(); 
		for(Map.Entry<String, Node> user : users.entrySet()) {
		 	Node current = users.get(user); 
		 /*
			if(message != null) {
				if(message.msgtype=leave) {
					send back message that says the person left to everyone
					if(from this user) {
						Socket socket = current.getSocket();
						socket.close(); 
						users.remove(user); 
					}
				}else if(message.msgtype=string) {
					send message to everyone 
				}else if(message.msgtype=join) {
					Send back message that says the person joined to everyone 
				} 
			}
		  check if they have a message to add to queue
			
		*/
		}
	}
	
	public void add(Socket newSocket) {
		Node newUser = new Node(newSocket); 
		Message message = newUser.getMessage();
		/*
		outToClient = newUser.getOutToClient();  
		inFromClient = newUser.getInFromClient();  
		//set messsage type to send the message to the user asking for name 
		//set message 
		try{
			outToClient.writeObject(message); 
			message = (Message) inFromClient.readObject(); 
		}catch(Exception e) {
			
		}
		//get the user name from message and set it to a variable 
		//users.put(name, newUser);  
		//adds message that person joined to messageQueue to let everyone know 
		*/
	}
	
}
