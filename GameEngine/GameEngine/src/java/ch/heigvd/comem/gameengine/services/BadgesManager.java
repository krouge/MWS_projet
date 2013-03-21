package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.model.Player;
import ch.heigvd.comem.gameengine.model.Rule;
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
    public List<Badge> findAll() {

        Query query = em.createQuery("SELECT b FROM Badge AS b");
        
        List<Badge> listBadge = (List<Badge>)query.getResultList();
        
        return listBadge;        
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
    public void delete(Long badgeId) {
        
        Badge badge = em.find(Badge.class, badgeId);
        
        Query query = em.createQuery("SELECT r FROM Rule AS r WHERE r.badge=:badge");
        query.setParameter( "badge", badge );
        
        for(Rule rule : (List<Rule>)query.getResultList()) {
            rule.setBadge(null);
        }
        
        badge.setRule(null);

        em.flush();
        em.remove(badge);
    }
    
}
