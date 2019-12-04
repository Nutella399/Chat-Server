import java.net.*; 
import java.io.*; 

class Server{

	public Server() {
	
	}
	
	public void run_server(int port)throws IOException {
		ServerSocket server = new ServerSocket(port);
		ChatThread chat = new ChatThread(); 
		Thread thread = new Thread(chat); 
		thread.start(); 
		
		while(true) {
			Socket user = server.accept(); 
			chat.add(user);
		}
	}
	
	public static void main(String[] args) {
		try{
			Server server = new Server(); 
			server.run_server(5000); 
		} catch(IOException e) {
			System.out.println(e.getMessage()); 
		}
	}
}
