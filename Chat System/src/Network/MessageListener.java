package Network;
import java.io.*;
import java.net.*;
import Data.*;

public class MessageListener extends Thread{
    public static int MAX_LOG = 50 ;
	private ServerSocket serverSocket;
	private LocalUser localHost ;
	private boolean	running ;
	
	public MessageListener(LocalUser user){
		try{
			localHost = user ;
			serverSocket = new ServerSocket(localHost.getServerPort(),MAX_LOG,localHost.getIpAddress());
			running = true ;
		}catch(Exception e){
			
		}
	}
	
	private Object deserialize(byte[] data) {
		Object retrieved = null ;
		try{
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			retrieved = (Object) is.readObject();
		}catch(Exception e){
			
		}
		return retrieved ;
	}
	
	public void run(){
		while(isRunning()){
			try{
				Socket remote = serverSocket.accept();
				byte [] byte_data = new byte [65535];					
		        InputStream is = remote.getInputStream();				
		        int bytesRead = is.read(byte_data,0,byte_data.length);	
		        int current = bytesRead;
		        do {
		           bytesRead = is.read(byte_data, current, (byte_data.length-current));
		           if(bytesRead >= 0) current += bytesRead;
		        } while(bytesRead > -1);

		        Object data = deserialize(byte_data);	
		        
	        	Message packet_msg = (Message) data;
	        	
		        //localHost.addMessage(message,pseudo);  ---------- find a way to add sent message to exchange session
			}catch(Exception e){
				
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void close(){
		try{
			serverSocket.close();
		}catch(Exception e){
			
		}
	}
}
