package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import javax.ejb.Local;

/**
 *
 * @author Julien Biedermann
 */
@Local
public interface ApplicationsManagerLocal {
    
    Long create(String name, String description, String apiKey, String apiSecret);

    Application find(Long applicationId);

    Application update(Long applicationId, String name, String description, String apiKey, String apiSecret);
    
    void remove(Long applicationId);
    
}
