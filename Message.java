import java.util.HashMap;
class Message implements Comparable<Message>{
	//has to be comparable
	//this is compare on the message type where 
	//leaving highest, then joining, then a string message
	//maybe have message hold the users name as well
	
	//client_init = 5, client_message = 4, client_leave = 6, 
	//server_init = 1, server_message = 2, server_leave = 3  
	private HashMap<String, User> users;
	private int msgType; 
	private String msg; 
	private String userName; 
	
	public Message(){
		msgType = -1; 
		msg = null;
	
	}
	
	public String getMsg() {
		return msg; 
	}
	
	//this string should be coming to this object with the user name and time already added
	public void setMsg(String message) {
		this.msg = message; 
	}
	
	public void setMsg() {
		switch(msgType){
			case 1:
				msg = "What is your username?"; 
				break;
			case 5:
				msg = userName + ": has joined the server";
				break;
			case 6:
				msg = userName + ": is leaving the server";
				break;		    
		}
	}
	
	public int getMsgType() {
		return msgType; 
	} 
	
	public void setMsgType(int type) {
		this.msgType = type; 
	}	
			
	public String getUserName() {
		return userName; 
	}
	
	public void setUserName(String name) {
		this.userName = name; 
	}
	
	//make sure this is done correctly so it brings the highest to the top 
	@Override
	public int compareTo(Message other){
		return other.msgType - this.msgType;  
	}
} 
