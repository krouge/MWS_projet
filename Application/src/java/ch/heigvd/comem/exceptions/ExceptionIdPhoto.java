/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.exceptions;

/**
 *
 * @author Jonas
 */
public class ExceptionIdPhoto extends Exception {
    
    private static String message = "Exception Id: l'id entré n existe pas dans la BDD";
    
    
    
    public ExceptionIdPhoto(){
        super(message);
    }
    
}
