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
				System.out.println("Listening...");
				Socket remote = serverSocket.accept();
				System.out.println("Message accepted");
				BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
                String input = in.readLine() ;
                System.out.println("Client sent : \t" + input);				
				
                /*
				byte [] byteData = new byte [65535];					
		        InputStream is = remote.getInputStream();				
		        int bytesRead = is.read(byteData,0,byteData.length);	
		        int current = bytesRead;
		        while(bytesRead > -1) {
		           bytesRead = is.read(byteData, current, (byteData.length-current));
		           if(bytesRead >= 0) current += bytesRead;
		        } 

		        Object data = deserialize(byteData);	
				System.out.println("Message deserialized");
	        	Message msg = (Message) data;
	        	*/
					        	
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
