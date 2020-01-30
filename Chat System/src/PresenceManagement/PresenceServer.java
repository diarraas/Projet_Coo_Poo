package PresenceManagement;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.*;

import Data.* ;

public class PresenceServer {
	public List<RemoteUser> onlineUsers ;
	public static final int PRESENCE_PORT = 9657 ; 
	
	private ServerSocket serverSocket ;
	private boolean running ;
	
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
	
	private void sendUserList() {
		UserList userList = new UserList(onlineUsers);
		try {
			
		}catch(Exception e) {
			
		}
		
	}

	 
	
	private boolean isRunning() {
		return running;
	}
	
	
	
	
	
}
