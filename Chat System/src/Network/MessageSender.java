package Network;
import java.net.*;
import java.sql.Date;
import java.text.DateFormat;

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
			e.printStackTrace();
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
			e.printStackTrace();
	    }
	    return data;
	}
	
	public void sendMessage(String msg){
		try {
			User remoteUser = localHost.findUserByAddress(serverAddr);
			String 	date = (DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)).format(new Date(System.currentTimeMillis()));	
			Message sentMessage = new Message(localHost.getLogin(),remoteUser.getLogin(),date,msg);
			byte[] serialized = serialize(sentMessage);			    	
	    	OutputStream os = clientSocket.getOutputStream();		
	        os.write(serialized,0,serialized.length);
	        os.flush();												
	        os.close();	 
	        synchronized(this) {
	        	if(!sentMessage.getBody().equals("end"))	localHost.findSessionWith(remoteUser.getLogin()).addMessage(sentMessage);
	        }	        	
	        clientSocket.close();
		}catch(Exception e){
	    	System.out.println("Erreur d'envoi de message en raison de : \t " + e.getMessage());
	    	e.printStackTrace();
		}
		
	}
	
	public void sendFile(String path) {
        try{
			File myFile = new File (path);							
	        String name = myFile.getName();							
	        byte [] fileBuffer  = new byte [(int)myFile.length()];	
	        FileInputStream fis = new FileInputStream(myFile);		
	        BufferedInputStream bis = new BufferedInputStream(fis);	
	        bis.read(fileBuffer,0,fileBuffer.length);					
	        bis.close();	
	        ChatFile sentFile= new ChatFile(name,fileBuffer);
	        byte[] serialized = serialize(sentFile);				
	        OutputStream os = clientSocket.getOutputStream();		
	        os.write(serialized,0,serialized.length);
			sendMessage(localHost.getLogin() + " a envoyé le fichier "+ name);
	        os.flush();												
	        os.close();	 
	        clientSocket.close();
        }catch(Exception e){
	    	System.out.println("Erreur d'envoi de fichier en raison de : \t " + e.getMessage());
	    	e.printStackTrace();
		}
    }
	
	public void close() {
		try{
			clientSocket.close();
		}catch(Exception e) {
			System.out.println("Erreur de fermeture de socket");
			e.printStackTrace();
		}
	}
}