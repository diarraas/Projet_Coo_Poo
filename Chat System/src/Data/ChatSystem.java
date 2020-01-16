package Data;

import java.io.* ;

public class ChatSystem {

	public static void main(String[] args) throws InterruptedException, IOException {
			
			//tests
			LocalUser user = new LocalUser("") ;
			user = user.createAccount();
			user.authentify();
			System.out.println("Entrer nom user");
			BufferedReader reader =
	                new BufferedReader(new InputStreamReader(System.in));
			String me = reader.readLine();
			user.startSession(me);
			user.sendMessage(me);
			
			user.disconnect();
			System.out.println("Entrer nom user");
			reader =
	                new BufferedReader(new InputStreamReader(System.in));
			me = reader.readLine();
			user.startSession(me);
			user.sendMessage(me);
			user.authentify();
			
		
			//Profile user =  Profile.create_account();
			//user.authentify();
	}

}
