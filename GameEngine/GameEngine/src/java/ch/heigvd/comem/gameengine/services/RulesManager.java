/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.model.Rule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Renouille
 */
@Stateless
public class RulesManager implements RulesManagerLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Long create(String eventType, int numberOfPoints, Badge badge) {
        Rule rule = new Rule();
        
       rule.setEventType(eventType);
       rule.setNumberOfPoints(numberOfPoints);
       rule.setBadge(badge);
       em.persist(rule); em.flush();
        
        return rule.getId();
    }
}
