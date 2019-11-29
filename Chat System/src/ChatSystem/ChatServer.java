package ChatSystem;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ChatServer implements ServerFunctions {

	private InetAddress server_address ;
	
	private int server_port ;

	private List<Socket> onliners ;
	
	private Socket exp,dest ;
	
	private ServerSocket server_socket;
	
	public ChatServer()  throws IOException {
		server_socket = new ServerSocket(12000,50,InetAddress.getLocalHost());
	}
	
	@Override
	public void accept() throws IOException {
		exp = server_socket.accept();
	}

	@Override
	public Message receive_message (Socket destinateur) throws IOException {
		//BufferedReader in = new BufferedReader(new InputStreamReader(exp.getInputStream()));
		//PrintWriter out = new PrintWriter(exp.getOutputStream(),true);
        //dest = dest2 ;
		return null ;
	}

	@Override
	public void update_online_users(Profile new_user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close_connection(Socket client) {
		// TODO Auto-generated method stub

	}
	
	public InetAddress getServer_address() {
		return server_address;
	}

	public void setServer_address(InetAddress server_address) {
		this.server_address = server_address;
	}

	
	public int getServer_port() {
		return server_port;
	}

}
