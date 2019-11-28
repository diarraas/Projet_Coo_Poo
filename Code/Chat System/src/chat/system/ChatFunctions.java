/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

/**
 *
 * @author diarra
 */import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface ChatFunctions {
    
    public Profil create_account() throws IOException ;
    
    public void change_login () throws IOException ;
    
    public void authentify();
    
    public void show_users();
    
    public void send_message();
    
    public void end_session();
    
    
}
