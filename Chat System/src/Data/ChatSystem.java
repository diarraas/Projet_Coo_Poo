package Data;

import java.io.* ;

public class ChatSystem {

	public static void main(String[] args) throws InterruptedException, IOException {
			
			//tests
		System.out.println("Entrer pseudo pour créer compte");
		
		BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
		String Me = reader.readLine();
		LocalUser user = LocalUser.createAccount(Me);
		user.authentify(user.getLogin());
			
		System.out.println("Entrer message");
		reader =  new BufferedReader(new InputStreamReader(System.in));
		String me = reader.readLine();
		user.startSession(user.getLogin());
		user.sendMessage(user.getLogin(),me);
		user.changeLogin("LEAH");
			

	}

}
