//package ChatSystem;
import java.io.IOException;
import java.net.*;

public interface ServerFunctions {
	
	//Listens and accept connections from a client_socket and returns said socket
	
	public void accept () throws IOException;
	
	
	//Retrieves streams packets from a socket
	
	public void receive_message(Socket dest) throws IOException ;
	
	
	//When a new client connects to server, server notifies the registers
	
	public void update_online_users(Profile new_user) ;
	
	
	//Close the connection with a client ---- must update online users 
	
	public void close_connection(Socket client);
}
