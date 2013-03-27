/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.config;

import com.sun.jersey.api.client.ClientResponse;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.net.ssl.SSLEngineResult.Status;
import javax.ws.rs.core.Response;

/**
 *
 * @author fabiencornaz
 */
public class GestionnaireGameEngine {
    
    public static final String API_SECRET = "1234";
    public static final String API_KEY = "1234";
    public static final int PORT = 8080;
    
    
    public static String getErrors(ClientResponse response){
        
        String error = new String();
        
        switch (response.getStatus()) {
            case 500:
                error = "Error 500 Internal Server Error";
                break;
                
            case 404:
                error = "Error 404 Not Found";
                break;
                
            case 204:
                error = "Erreur 204 No Content: Requête traitée avec succès mais pas d’information à renvoyer";
                break;
                
            default:
                throw new AssertionError();
                
        
        }
        
        return error; 
    }

        
        
    
        
    
    
}
