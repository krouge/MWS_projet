package ch.heigvd.comem.gameengine.exceptions;

/**
 * Exception lancée lorsque le badge n'est pas trouvé
 * @author Julien Biedermann
 */
public class BadgesExceptions extends Exception {
    
    private static String message = "test";
    
    public BadgesExceptions() {
        super(message);
        
    }

}
