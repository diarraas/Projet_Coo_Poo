package ChatSystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Chat_server implements ServerFunctions {

	private InetAddress server_address ;

	private int server_port ;
	
	private List<Socket> onliners ;
	
	
	@Override
	public Socket accept() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receive_message(Socket exp) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update_online_users(Profile new_user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close_connection(Socket client) {
		// TODO Auto-generated method stub

	}

}
