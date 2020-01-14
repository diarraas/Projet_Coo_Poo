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
			clientSocket = new Socket(serverAddr, serverPort);
		}catch(Exception e){
			
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
	    	
	    }
	    return data;
	}
	
	public void sendMessage(String msg){
		try {
			System.out.println("Sending message");
			Message sentMessage = new Message(localHost,localHost.findUserByAddress(serverAddr),msg);	
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
    		out.println(sentMessage.toString());
			/**byte[] serialized_msg = serialize(sentMessage);			    	
	    	OutputStream os = clientSocket.getOutputStream();		//retrieves the output stream of the socket
	        os.write(serialized_msg,0,serialized_msg.length);		//writes the bytes into the stream
	        os.flush();												//flushes the stream
	        os.close();*/	        									//closes the stream
	        
			clientSocket.close();
		}catch(Exception e){
			
		}
		
	}
}
