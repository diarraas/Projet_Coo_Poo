package ChatSystem;
import java.io.IOException;
import java.net.*;

public interface ServerFunctions {
	
	//Listens and accept connections from a client_socket and returns said socket
	
	public Socket accept();
	
	
	//Retrieves streams/packets from a socket and return said streams in Message form
	
	public Message receive_message(Socket exp) throws IOException ;
	
	
	//When a new client connects to server, server notifies the registers
	
	public void update_online_users(Profile new_user) ;
	
	
	//Close the connection with a client ---- must update online users 
	
	public void close_connection(Socket client);
}
