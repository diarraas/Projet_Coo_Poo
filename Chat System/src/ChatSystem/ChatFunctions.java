package ChatSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface ChatFunctions {
	
	public Profile create_account() throws IOException ;
    
    public void change_login () throws IOException ;
    
    public void authentify() throws IOException ;
    
    public void show_users();
    
    public void send_message(Profile dest) throws IOException;
    
    public void end_session() throws IOException;
}
