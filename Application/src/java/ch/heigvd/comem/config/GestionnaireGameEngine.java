package ch.heigvd.comem.config;

import com.sun.jersey.api.client.ClientResponse;

/**
 *
 * @author fabiencornaz
 */
public class GestionnaireGameEngine {
    
    public static final String API_SECRET = "1234";
    public static final String API_KEY = "1234";
    public static final int PORT = 8081;
    
    
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
