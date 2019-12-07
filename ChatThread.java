import java.net.*; 
import java.io.*; 
import java.util.*; 

class ChatThread implements Runnable{
	
	private HashMap<String, User> userMap; 
	private PriorityQueue<Message> messageQueue;	
	private ObjectOutputStream outToServer; 
	private ObjectInputStream inFromServer; 
	
	public ChatThread() {
		userMap = new HashMap<>(); 
		messageQueue = new PriorityQueue<>();
	}
	
	@Override	
	public void run() {
		System.out.println("Running....");

		Message message = messageQueue.poll(); 
		for(Map.Entry<String, User> user : userMap.entrySet()) {
	     outToServer = new ObjectOutputStream(user.getOutputStream()); 
		 message.setMsgType(1); 
	     message.setMsg();
		 outToServer.writeObject(message);  
		 inFromServer = new ObjectInputStream(((Socket) user).getInputStream());		
		 message = (Message) inFromServer.readObject(); 
		 System.out.println("From Client 1: " + message.getMsg());
		  User current = user.getValue(); 
		  System.out.print(current);
			if(message != null) {
				int msgType = message.getMsgType(); 
				if(msgType == 6) {
					current.sendMessage(3); 
					if(message.getUserName() == user.getKey()) {
						try{
							Socket socket = current.getSocket();
							socket.close(); 
							userMap.remove(user); 
						}catch(IOException e) {
							System.out.println(e);
						}
					}
				}else if(msgType == 5) {
					current.sendMessage(1); 
				}else if(msgType == 4) {
					current.sendMessage(2);  
				}
			}
		  //check if they have a message to add to queue
			Message newMessage = current.getMessage();  
			messageQueue.add(newMessage); 	
		}
	}
	
	public void add(Socket newSocket) {
		User newUser = new User(newSocket); 
		newUser.sendMessage(2);
		Message message = newUser.getMessage();  
		String name = message.getUserName(); 
		userMap.put(name, newUser);  
		messageQueue.add(message); 
	}
	
}
