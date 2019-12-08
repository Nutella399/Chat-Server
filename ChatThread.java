import java.net.*; 
import java.io.*; 
import java.util.*; 
import java.util.Map.*; 

class ChatThread implements Runnable{
	
	private HashMap<String, User> userMap; 
	private HashMap<String, User> needAdded; 
	private Iterator<Entry<String, User>> iterator; 
	private Iterator<Entry<String, User>> addingIterator; 
	private PriorityQueue<Message> messageQueue;	
	private boolean adding;
	private int msgType;  
	
	public ChatThread() {
		userMap = new HashMap<String, User>(); 
		needAdded = new HashMap<String, User>(); 
		iterator = userMap.entrySet().iterator(); 
		addingIterator = needAdded.entrySet().iterator();
		messageQueue = new PriorityQueue<>();
		adding = false; 
		msgType = -1;
	}
	
	@Override	
	public void run() {
		System.out.println("Running....");
		while(true){
			Message polledMessage = messageQueue.poll();
			iterator = userMap.entrySet().iterator(); 
			 
			while(iterator.hasNext()) {
				Map.Entry<String, User> next = (Map.Entry<String, User>) iterator.next(); 
				User current = userMap.get(next.getKey()); 
			  if(polledMessage != null) {
			  	System.out.println("From Client: '" + polledMessage.getMsg() + "'"); 
					msgType = polledMessage.getMsgType(); 
					System.out.println("type: " + msgType); 	
					if(msgType == 6) {
						if(polledMessage.getUserName() == next.getKey()) {
							try{
								current.sendMessage(0, polledMessage.getMsg()); 
								Socket socket = current.getSocket();
								socket.close(); 
								userMap.remove(next.getKey()); 
							}catch(IOException e) {
								System.out.println(e);
							}
						}else {
							current.sendMessage(3, polledMessage.getMsg()); 
							System.out.println("To Client: " + polledMessage.getUserName() + polledMessage.getMsg()); 
							System.out.println("type: 3"); 		
						}
					}else if(msgType == 5) {
						current.sendMessage(2, polledMessage.getMsg()); 
						System.out.println("To Client: " + polledMessage.getUserName() + polledMessage.getMsg());
						System.out.println("type: 2"); 	 
					}else if(msgType == 4) {
						current.sendMessage(2, polledMessage.getMsg());  
						System.out.println("To Client: " + polledMessage.getUserName() + polledMessage.getMsg());
						System.out.println("type: 2"); 	 
					}
				}
				
				if(current.getMessageReady()) {
					System.out.println("here");
					Message newMessage = current.getMessage();
					if(newMessage.getMsgType() != -2) {
						messageQueue.add(newMessage); 	
						System.out.println("message added: " + newMessage.getMsg());
					}else {
						System.out.println("From Client: confirmation"); 
					}
				}
				
				/*Message newMessage = current.getMessage(); 
				if(newMessage.getMsgType() != -2) {
					messageQueue.add(newMessage); 	
					System.out.println("message added: " + newMessage.getMsg());
				}else {
					System.out.println("From Client: confirmation"); 
				} */
				
				 
			}
			if(adding == true && msgType == 5) {
				addingIterator = needAdded.entrySet().iterator(); 
				while(addingIterator.hasNext()) {
					Map.Entry<String, User> nextAdded = (Map.Entry<String, User>) addingIterator.next(); 
					userMap.put(nextAdded.getKey(), nextAdded.getValue());
					System.out.println("user added " + nextAdded.getKey());
					needAdded.remove(nextAdded.getKey()); 
				}
				adding = false; 
			}
		}
	}
	
	public void add(Socket newSocket) {
		User newUser = new User(newSocket);
		
		newUser.sendMessage(1);
		Message message = newUser.getMessage(1);
		adding = true; 
		newUser.sendMessage(2, "Welcome to the Chat");  
		String name = message.getUserName(); 
		newUser.setUserName(name); 
		needAdded.put(name, newUser); 
		messageQueue.add(message); 
		msgType = 5;  
		System.out.println("message added: " + messageQueue.peek().getMsg());
		System.out.println("queue size:  " + messageQueue.size());
		Thread thread = new Thread(newUser); 
		thread.start();  
		System.out.println(name + ": thread starts");
		
	}
}
