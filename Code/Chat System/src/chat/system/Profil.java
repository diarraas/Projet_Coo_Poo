/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *
 * @author diarra
 */
public class Profil implements ChatFunctions {
    
    static int justAthought = 0 ;
    
    private int id ;
    
    private String login ;
    
    private boolean status ;
    
    public Profil(String log){
        login = log ;
        
        // id = find unique random number
         id = justAthought ;
         
         justAthought ++ ;
                 
        status = true ;
    }
    
    
    public Profil create_account() throws IOException{
       System.out.println("Choisissez un login");
        BufferedReader reader =
                   new BufferedReader(new InputStreamReader(System.in));
        String log = reader.readLine(); 
        boolean isUnic = SystemRegister.verify_unicity(log);
        while(!isUnic){
            //choose as many times as desired ....
            System.out.println("Login existant");
            System.out.println("Entrez un nouveau login");
            reader = new BufferedReader(new InputStreamReader(System.in));
            log = reader.readLine() ;
            isUnic = SystemRegister.verify_unicity(log);
        }
                
        return new Profil(log) ;
    }
    
    
    public void change_login()throws IOException {
        // test unicity oflogin ------ 
        System.out.println("Entrez un nouveau login");
        BufferedReader reader =
                   new BufferedReader(new InputStreamReader(System.in));
        String newLog = reader.readLine(); 
        boolean isUnic = SystemRegister.verify_unicity(newLog);
        while(!isUnic){
            //choose as many times as desired ....
            System.out.println("Login existant");
            System.out.println("Entrez un nouveau login");
            reader = new BufferedReader(new InputStreamReader(System.in));
            newLog = reader.readLine() ;
            isUnic = SystemRegister.verify_unicity(newLog);
        }
        
        login = newLog ;

    }
    
    public void authentify (){
        if(SystemRegister.users.contains(this)){
            status = true ;
        } else {
            System.out.println("Erreur d'authentification");
        }
    
    }
        
    public void show_users() {
    
    }
    
    public void send_message(){
    
    }
    
    public void end_session(){
        status = false ;
    
    }
    
    public int getId(){
        return id ;
    }
    
    
    public String getLogin(){
        return login ;
    }
    
    public boolean getStatus(){
        return status ;
    }
}
