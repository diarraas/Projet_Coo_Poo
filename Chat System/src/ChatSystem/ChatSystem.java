package ChatSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatSystem {

	public static void main(String[] args) {
		
		/*
		 log in (soit creer soit auth)
		 UDP BC pour dire qu'on est en ligne et récup list des gens online
		 */
		
		Profile profile = Profile.create_account() ;
			profile.authentify();
			
		/*Connected
		 Thread 1 = accept() -> en attente d'une demande de clavardage
		 Thread 2 = Le reste (envoi de msg)
		 */
			
	}

}
