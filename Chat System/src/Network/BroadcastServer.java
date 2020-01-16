package Network;

import java.net.*;

import Data.LocalUser;
import Data.RemoteUser;

public class BroadcastServer extends Thread {
	
    public static int BROADCAST_PORT = 4445 ;
    private DatagramSocket broadcastSocket;
	private byte[] buf = new byte[65535];
	private LocalUser localHost ;
	private boolean running ;

	public BroadcastServer(LocalUser user) {
		localHost = user ;
		
		try{
			
			broadcastSocket = new DatagramSocket(BROADCAST_PORT);
			
		}catch(Exception e){
			
    		System.out.println("Erreur de creation du serveur UDP en raison de : \t " + e.getMessage());
			
		}
	}
	
	public void run() {
		while(isRunning()){
			try{
	        	DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        	broadcastSocket.receive(packet);
	            String received = new String(packet.getData(), 0, packet.getLength());
	            String[] infos = received.split(" ");
	         	if(infos[1].contentEquals("login")) {
	         		InetAddress address = packet.getAddress();
		            int port = packet.getPort();
		            String myinfos = localHost.getLogin() + " login " + localHost.getIpAddress().getHostAddress() + " " + localHost.getServerPort() ;  
			        buf = myinfos.getBytes();
	         		packet = new DatagramPacket(buf, buf.length, address, port);
		            broadcastSocket.send(packet);
		            address = InetAddress.getByName(infos[2]);		            
		            System.out.println("Nouvelle connexion, maj de la liste des onliners\t" );
		            RemoteUser newUser = new RemoteUser(infos[0],address);
		            
		            synchronized(this){
		               localHost.addUser(newUser);
		            }
		            System.out.println("New onliners list \t" + localHost.getOnliners().toString() );
	         	}else if(infos[1].contentEquals("logoff")) {
		           	System.out.println("Nouvelle deconnexion, maj de la liste des onliners\t");   	
		            localHost.removeUser(localHost.findUserByLogin(infos[0]));
		            System.out.println("New onliners list \t" + localHost.getOnliners().toString() );

		        }else if(infos[1].contentEquals("change")) {
		           	System.out.println("Changement de login retenu, new login is \t"+ infos[0]);
		           	System.out.println("New onliners list \t" + localHost.getOnliners().toString());
		           	System.out.println("Address is supposed to be \t" + infos[2]);
		           	System.out.println("INET ADDRESS IS "+InetAddress.getByName(infos[2]));
		           	localHost.findUserByAddress(InetAddress.getByName(infos[2])).setLogin(infos[0]);
		            System.out.println("New onliners list \t" + localHost.getOnliners().toString() );

		        }   	
		        
			}catch(Exception e){
				if(!isRunning()) {
					System.out.println("Serveur arrêté");
				}else {
					System.out.println("Erreur de lancement du serveur UDP en raison de : \t " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		broadcastSocket.close();
     }
		

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
}