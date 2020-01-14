package Network;
import java.net.*;
import Data.*;
import java.io.*;
public class MessageSender {
	private Socket clientSocket ;
	private InetAddress serverAddr ;
	private int serverPort ;
	private LocalUser localHost ;
	
	public MessageSender(LocalUser user, InetAddress remoteAddr, int remotePort){
		try{
			localHost = user ;
			serverAddr = remoteAddr ;
			serverPort = remotePort ;
			clientSocket = new Socket(serverAddr, serverPort);
		}catch(Exception e){
			System.out.println("Erreur de création du client TCP en raison de : \t " + e.getMessage());
		}

	}
	
	public static byte[] serialize(Object packet) {
		byte [] data = null ;
		
		try {
	    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ObjectOutputStream os = new ObjectOutputStream(out);
		    os.writeObject(packet);
		    data = out.toByteArray();
	    }catch(Exception e){
	    	System.out.println("Erreur de serialisation en raison de : \t " + e.getMessage());
	    }
	    return data;
	}
	
	public void sendMessage(String msg){
		try {
			Message sentMessage = new Message(localHost.findUserByAddress(serverAddr).getLogin(),msg);
			byte[] serialized = serialize(sentMessage);			    	
	    	OutputStream os = clientSocket.getOutputStream();		
	        os.write(serialized,0,serialized.length);
	        os.flush();												
	        os.close();	        									
			clientSocket.close();
		}catch(Exception e){
	    	System.out.println("Erreur d'envoi de message en raison de : \t " + e.getMessage());

		}
		
	}
}
