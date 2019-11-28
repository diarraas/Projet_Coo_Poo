/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system ;
import java.util.* ;

/**
 *
 * @author diarra
 */

public class SystemRegister {
    
    public static List<Profil> onliners ;
    
    public static List<Profil> users ;
    
    public static void update_users(Profil user){
        remove_user(user, users) ;
    
    }
    
    public static void update_online_users (){}
    
    public static void add_user (Profil user){
        users.add(user.getId(), user);
    }
    
    public static void add_online_user(Profil user){
        onliners.add(user);
    }
    
    public static void remove_user(Profil user, List<Profil> list){
        list.remove(user.getId());
    }
    
    public static boolean verify_unicity(String login){
        boolean is_unique = users.get(0).getLogin().equalsIgnoreCase(login) ;
        if(is_unique){
            ListIterator<Profil> iterator = users.listIterator(1) ;
            Profil temp = null ;
            while(is_unique && iterator.hasNext()){
                temp = iterator.next() ;
                is_unique = temp.getLogin().equalsIgnoreCase(login) ;
            }
        }
        return is_unique ;        
    }
    
}
