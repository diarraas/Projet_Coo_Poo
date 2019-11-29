package ChatSystem;

public class ChatSystem {

	public static void main(String[] args) {
		if(args[0].equals("server")) {
			try {
				ChatServer chat = new ChatServer() ;
				chat.accept();
			}catch(Exception e) {}
			
		}

	}

}
