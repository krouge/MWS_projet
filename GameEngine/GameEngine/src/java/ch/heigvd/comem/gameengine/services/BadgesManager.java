package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Julien Biedermann
 */
@Stateless
@WebService
public class BadgesManager implements BadgesManagerLocal {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Long create(String name, String description, String source) {
        
        Badge badge = new Badge();
        badge.setName(name);
        badge.setDescription(description);
        badge.setSource(source);
        em.persist(badge); em.flush();
        
        return badge.getBadgeId();
        
    }
    
    @Override
    public Badge find(Long badgeId) {
        
        Badge badge = em.find(Badge.class, badgeId);
        
        return badge;
        
    }
    
    @Override
    public Badge update(Long badgeId, String nom, String description, String source) {
        
        Badge badge = em.find(Badge.class, badgeId);
        badge.setName(nom);
        badge.setDescription(description);
        badge.setSource(source);
        
        return badge;
        
    }

    @Override
    public void remove(Long badgeId) {
        
        Badge badge = em.find(Badge.class, badgeId);
        em.remove(badge);
        
    }    

}
