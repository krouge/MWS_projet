package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author julien
 */
@Stateless
@WebService
public class BadgesManager implements BadgesManagerLocal {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Long createBadge(String name, String descrition, String source) {
        
        Badge badge = new Badge();
        badge.setName(name);
        badge.setDescription(descrition);
        em.persist(badge); em.flush();
        
        return badge.getBadgeId();
        
    }

    

}
