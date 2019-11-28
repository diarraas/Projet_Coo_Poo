/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

/**
 *
 * @author diarra
 */
public interface Register {
    
    public void update_users();
    
    public void update_online_users () ;
    
    public void add_user (Profil user);
    
    public void add_online_user(Profil user);
    
    public void remove_user(Profil user);
    
    
}
