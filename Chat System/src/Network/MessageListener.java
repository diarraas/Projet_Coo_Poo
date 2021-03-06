package Network;
import java.io.*;
import java.net.*;
import java.util.ConcurrentModificationException;

import Data.*;
import GraphicUserInterface.DiscussionWindow;
import GraphicUserInterface.NotificationWindow;

public class MessageListener extends Thread{
    
	/**
	 * 
	 * Serveur d'écoute des messages à destination de l'utilisateur local
	 * 
	 * */
	public static int MAX_LOG = 50 ;
	private ServerSocket serverSocket;
	private LocalUser localHost ;
	private boolean	running ;
	
	public MessageListener(int serverPort, InetAddress ipAddress){
		
		try{
			serverSocket = new ServerSocket(serverPort,MAX_LOG,ipAddress);
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
	    	System.out.println("Erreur de desérialisation en raison de : \t " + e.getMessage());
		}
		return retrieved ;
	}
	
	public void run(){
		while(isRunning()){
			if(serverSocket != null) {
				try{
					final Socket remote = serverSocket.accept();
					Runnable messageHandler = new Runnable() {
						public void run() {
			            	try {
								byte [] byteData = new byte [65535];					
						        InputStream is = remote.getInputStream();
						        if(is!= null) {
							        int bytesRead = is.read(byteData,0,byteData.length);	
							        int current = bytesRead;
					
							        while(bytesRead > -1) {
							           bytesRead = is.read(byteData, current, (byteData.length-current));
							           if(bytesRead >= 0) current += bytesRead;
							        } 
							        
							        Object data = deserialize(byteData);
	
							        Class<? extends Object> object = data.getClass();
							        if(object !=null){
							        	if(object.getCanonicalName().equals(Message.class.getCanonicalName())){
							        		Message msg = (Message) data;
							        		String exp = msg.getExp();
						                	if(msg.getBody().equals("end")) {
						                		synchronized(this) {
						                			if(!localHost.getLogin().equals(exp)&&localHost.getOngoing().remove(localHost.findSessionWith(exp))){
						                				new NotificationWindow("Fin de session avec:  " + exp);
						                			}else {
						                				new NotificationWindow("Pas de session en cours avec " + exp);
						                			}
						                		}
						                	}else {
						                		synchronized(this) {
						                			localHost.startSession(exp);
						                			if(!exp.equals(localHost.getLogin()))	localHost.findSessionWith(exp).addMessage(msg);
						                		}
						                		Database.addMessage(msg);
						                		DiscussionWindow.updateSession(exp);
						                	}
							        	}else if(object.getCanonicalName().equals(ChatFile.class.getCanonicalName())){
							        		ChatFile file = (ChatFile) data ;
							        		
							        		File outfile = new File("Files/"+file.getName());
							        		 
							        		InputStream content = new ByteArrayInputStream(file.getContent());
							        		FileOutputStream outstream = new FileOutputStream(outfile);
							     
							        	    byte[] buffer = new byte[1024];
							     
							        	    int length;
			
							        	    while ((length = content.read(buffer)) > 0){
							        	    	outstream.write(buffer, 0, length);
							        	    }
	
							        	    content.close();
							        	    outstream.close();
							        		
							        	}
							        }
							   }
						   				                
			            	}catch(ConcurrentModificationException e) {} 
			            	
			            	catch ( Exception e) {
		                  		System.out.println("Erreur d'extraction du message en raison de :\t" + e.getMessage() );
		            			e.printStackTrace();
		                  }
		            }
		        };
		        
		        new Thread(messageHandler).start();
		        
				}catch(Exception e){
					if(!isRunning()) { 
						System.out.println("TCP server stopped");
					}else {
						System.out.println("Erreur de lancement du serveur TCP en raison de : \t " + e.getMessage());
						e.printStackTrace();
					}
				}
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
			System.out.println("Erreur de fermeture du serveur TCP en raison de : \t " + e.getMessage());
		}
	}
	
	public void setLocalHost(LocalUser localHost) {
		this.localHost = localHost;
	}
}
