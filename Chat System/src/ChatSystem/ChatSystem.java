package ChatSystem;

import java.io.* ;

public class ChatSystem {

	public static void main(String[] args) throws InterruptedException, IOException {
			
			//tests
			Profile user = Profile.create_account();
			user.authentify();
			Thread.sleep(2000);
			System.out.println("Donner pseudo to send");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		    String here = in.readLine() ;
			user.send_message(here);
			user.end_session();
			user.send_message(here);

	}

}
