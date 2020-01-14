package Data;

import java.io.* ;

public class ChatSystem {

	public static void main(String[] args) throws InterruptedException, IOException {
			
			//tests
			LocalUser user = LocalUser.create_account();
			user.authentify();
			user.send_message(user.getLogin());
			
		
			//Profile user =  Profile.create_account();
			//user.authentify();
	}

}
