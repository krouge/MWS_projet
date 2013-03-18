package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Julien Biedermann
 */
@Stateless
public class ApplicationsManager implements ApplicationsManagerLocal {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Long create(String name, String description, String apiKey, String apiSecret) {

        Application application = new Application ();
        application.setName(name);
        application.setDescription(description);
        application.setApiKey(apiKey);
        application.setApiSecret(apiSecret);
        
        return application.getApplicationId();
    
    }

    @Override
    public Application find(Long applicationId) {

        Application application = em.find(Application.class, applicationId);
        
        return application;
    
    }

    @Override
    public Application update(Long applicationId, String name, String description, String apiKey, String apiSecret) {

        Application application = em.find(Application.class, applicationId);
        application.setName(name);
        application.setDescription(description);
        application.setApiKey(apiKey);
        application.setApiSecret(apiSecret);
        
        return application;
    
    }

    @Override
    public void remove(Long applicationId) {

        Application application = em.find(Application.class, applicationId);
        em.remove(application);
    
    }

}
