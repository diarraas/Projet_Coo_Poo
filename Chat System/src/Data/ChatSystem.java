package Data;

import java.io.* ;

public class ChatSystem {

	public static void main(String[] args) throws InterruptedException, IOException {
			
			//tests
		
			LocalUser user = LocalUser.createAccount("Lea");
			user.authentify(user.getLogin());
			
			System.out.println("Entrer nom user");
			BufferedReader reader =
	                new BufferedReader(new InputStreamReader(System.in));
			String me = reader.readLine();
			user.startSession(user.getLogin());
			user.sendMessage(user.getLogin(),me);
			user.changeLogin("LEAH");
			

	}

}
