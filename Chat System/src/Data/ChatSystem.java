package Data;

import java.io.* ;

public class ChatSystem {

	public static void main(String[] args) throws InterruptedException, IOException {
			
			//tests
			LocalUser user = LocalUser.createAccount();
			user.authentify();
			Thread.sleep(2000); 
			BufferedReader reader =
	                new BufferedReader(new InputStreamReader(System.in));
			String me = reader.readLine();
			user.startSession(me);
			Thread.sleep(2000);
			user.sendMessage(me);
			
		
			//Profile user =  Profile.create_account();
			//user.authentify();
	}

}
