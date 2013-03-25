package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julien Biedermann
 */
@Stateless
@WebService
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
        em.persist(application); em.flush();
        
        return application.getApplicationId();
    
    }

    @Override
    public Application find(Long applicationId) {

        Application application = em.find(Application.class, applicationId);
        
        return application;
    
    }
    
    @Override
    public Application find(String apiKey, String apiSecret) {

        Query query = em.createQuery("SELECT a FROM Application AS a WHERE apiKey = :apiKey AND apiSecret = :apiSecret");
        query.setParameter("apiKey", apiKey);
        query.setParameter("apiSecret", apiSecret);
        Application application = (Application) query.getSingleResult();
        
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

    @Override
    public List<Application> findAll() {
        Query query = em.createQuery("SELECT a FROM Application AS a");
        
        List<Application> listApp = (List<Application>)query.getResultList();
        
        return listApp;        
    }

}
