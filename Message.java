import java.io.Serializable;
import java.util.HashMap;


public class Message implements Serializable
{ 
	public HashMap<String, Node> users;
	//message types
	static final int MSG_REQUEST_INIT = 1;//sent from server to client
	static final int MSG_RESPONSE_INIT = 2; //sent from client to server
	static final int MSG_REQUEST_PLAY = 3; //sent from server to client
	static final int MSG_RESPONSE_PLAY = 4;//sent from client to server
	static final int MSG_REQUEST_GAME_OVER = 5; //sent from server to client
	
	private int msgType=-1;
	private String msg = null;

	//getters
	public String getMsg(){
		return this.msg;
	}
	public void setMsg() {
		switch(msgType){
			case 1: 
				this.msg = "New user has been added."; 
				break; 
			case 2: 
				this.msg = users. + "Initilization complete."; 
				break; 
			case 3: 
				this.msg = "User has left."; 
				break;
			case 4: 
				this.msg = "Next move is placed."; 
				break;
			default: 
				this.msg = "Error"; 
				break;  
		}  
	}		

	
	public int getMsgType(){
		return this.msgType;
	}
	//setters

	public void setMsgType(int type){
		this.msgType = type;	
	}
	public void setMsg(String m){
		this.msg = m;
	}
	

	// constructor
	public Message() 
	{ 
		
	} 
	
} 
