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
	
	private static int MAX_CLIENT = 50 ;
	
	public ChatServer() {
		try {
			server_port = 1025 + 65535 * ((int) Math.random());
			server_address = InetAddress.getLocalHost() ;
			server_socket = new ServerSocket(server_port,MAX_CLIENT,server_address);
		}catch(Exception e) {
			System.out.println("Erreur création du server");
		}
	}
	
	@Override
	public void accept() {
		try {
			exp = server_socket.accept();
			onliners.add(exp);
		}catch (Exception e) {
			System.out.println("Erreur d'acception connexion");
		}
	}

	@Override
	public void receive_message (Socket destinataire){
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(exp.getInputStream()));
	        dest = destinataire ;
	        if(is_online(dest)){
	        	PrintWriter out = new PrintWriter(dest.getOutputStream(),true);
	        	out.println(in.readLine());        
	        }else{
	        	PrintWriter out = new PrintWriter(exp.getOutputStream(),true);
	        	out.println("Recipient offline");
	        }
		}catch (Exception e) {
			System.out.println("Erreur de reception du message");
		}
	}

	private boolean is_online(Socket dest2) {
		return onliners.contains(dest2);
	}

	@Override
	public void update_online_users(Profile new_user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close_connection(Socket client) {
		try{
			if(is_online(client)) client.close() ;
		}catch(Exception e){
			System.out.println("Erreur fermeture de connection");
		}

	}
	
	public InetAddress getServer_address() {
		return server_address;
	}

	
	public int getServer_port() {
		return server_port;
	}

}
