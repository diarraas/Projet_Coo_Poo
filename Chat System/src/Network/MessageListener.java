package Network;
import java.io.*;
import java.net.*;

import Data.*;
import GraphicUserInterface.ChatWindow;

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
					        InputStream wrappedStream = new PushbackInputStream(is, 65535);
					        int bytesRead = is.read(byteData,0,byteData.length);	
					        int current = bytesRead;
			
					        while(bytesRead > -1) {
					           bytesRead = is.read(byteData, current, (byteData.length-current));
					           if(bytesRead >= 0) current += bytesRead;
					        } 

					        Object data = deserialize(byteData);
					        if(data == null) System.out.println("data null ---- why ?");
					        Class<? extends Object> object = data.getClass();
					        if(object !=null){
					        	if(object.getCanonicalName().equals(Message.class.getCanonicalName())){
					        		Message msg = (Message) data;
					        		String exp = msg.getExp();
				                	localHost.startSession(exp);
				                	localHost.findSessionWith(exp).addMessage(msg);
				        	        Database.addMessage(msg);
				                	ChatWindow.updateSession(exp);
					        	}else if(object.getCanonicalName().equals(File.class.getCanonicalName())){
					        		System.out.println("Got a file");
					        		File file = (File) data ;
						        	FileOutputStream fos = new FileOutputStream("Chat System/Files/"+file.getName());
						            BufferedOutputStream bos = new BufferedOutputStream(fos);
					                byteData = new byte[65535];
							        bytesRead = 0;	
							        //N'entre pas de le while
							        ((PushbackInputStream) wrappedStream).unread(byteData, 0, current);
							        while((bytesRead=wrappedStream.read(byteData)) > -1) {
							        	bos.write(byteData,0,bytesRead);
									    System.out.println("Bytes read :" + bytesRead);
   
							        } 
					                bos.flush();
					        	}
					        }
			                	
			                
		            	} catch ( Exception e) {
	                  		//System.out.println("Erreur d'extraction du message en raison de :\t" + e.getMessage() );
	            			e.printStackTrace();
	                  }
	            }
	        };
	        
	        new Thread(messageHandler).start();
	        
			}catch(Exception e){
		    	System.out.println("Erreur de lancement du serveur TCP en raison de : \t " + e.getMessage());
				e.printStackTrace();

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
