package Data;

import java.io.* ;

public class ChatSystem {

	public static void main(String[] args) throws InterruptedException, IOException {
			
			//tests
			LocalUser user = LocalUser.create_account();
			user.authentify();
			System.out.println("Donner pseudo to send");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		    String here = in.readLine() ;
			user.send_message(here);
			
		
			//Profile user =  Profile.create_account();
			//user.authentify();
	}

}
