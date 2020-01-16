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
		}catch(Exception e){
	    	System.out.println("Erreur de création du serveur TCP en raison de : \t " + e.getMessage());
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
				Runnable messageHandler = new Runnable() {
		            public void run() {
		            	try {
							byte [] byteData = new byte [65535];					
					        InputStream is = remote.getInputStream();				
					        int bytesRead = is.read(byteData,0,byteData.length);	
					        int current = bytesRead;
			
					        while(bytesRead > -1) {
					           bytesRead = is.read(byteData, current, (byteData.length-current));
					           if(bytesRead >= 0) current += bytesRead;
					        } 
			
					        Object data = deserialize(byteData);
				        	Message msg = (Message) data;
			                String exp = msg.getExp();
							localHost.startSession(exp);
				        	System.out.println(msg.toString());
					        localHost.findSessionWith(exp).addMessage(msg);
					        Database.addMessage(msg);
		            	} catch ( Exception e) {
	                  		System.out.println("Erreur d'extraction du message en raison de :\t" + e.getMessage() );
	                  }

	            }
	        };
	        
	        new Thread(messageHandler).start();
	        
			}catch(Exception e){
		    	System.out.println("Erreur de lancement du serveur TCP en raison de : \t " + e.getMessage());
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
