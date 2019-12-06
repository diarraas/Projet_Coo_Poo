package ChatSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatSystem {

	public static void main(String[] args) {
		
			Profile user = Profile.create_account();
			user.authentify();
	}

}
