package ChatSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface ChatFunctions {
	
	public Profile create_account() throws IOException ;
    
    public void change_login () throws IOException ;
    
    public void authentify();
    
    public void show_users();
    
    public void send_message(Profile dest);
    
    public void end_session() throws IOException;
}
