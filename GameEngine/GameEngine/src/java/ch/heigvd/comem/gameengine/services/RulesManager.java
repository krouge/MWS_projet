/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.model.Rule;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Renouille
 */
@Stateless
@WebService
public class RulesManager implements RulesManagerLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Long create(String eventType, int numberOfPoints, Long applicationId, Long badgeId) {
        
        Rule rule = new Rule();
        
       rule.setEventType(eventType);
       rule.setNumberOfPoints(numberOfPoints);
       rule.setApplication(applicationId);
       rule.setBadge(badgeId);
       em.persist(rule); em.flush();
        
        return rule.getRuleId();
    }

    @Override
    public Long create(String eventType, int numberOfPoints, Long application) {
        
        Rule rule = new Rule();
        
        rule.setEventType(eventType);
        rule.setNumberOfPoints(numberOfPoints);
        rule.setApplication(application);
        em.persist(rule); em.flush();
        
        return rule.getRuleId();
    }
    
    @Override
    public Rule find(Long ruleId) {
        
        Rule rule = em.find(Rule.class, ruleId);
        
        return rule;
        
    }

    @Override
    public void remove(Long ruleId) {
        
        Rule rule = em.find(Rule.class, ruleId);
        em.remove(rule);
        
    }

    @Override
    public Rule update(Long ruleId, String eventType, int numberOfPoints, Long applicationId, Long badgeId) {
        
        Rule rule = em.find(Rule.class, ruleId);
        
        rule.setEventType(eventType);
        rule.setNumberOfPoints(numberOfPoints);
        rule.setApplication(applicationId);
        rule.setBadge(badgeId);
        
        return rule;
    }

    @Override
    public Rule updage(Long ruleId, String eventType, int numberOfPoints, Long applicationId) {
        
        Rule rule = em.find(Rule.class, ruleId);
        
        rule.setEventType(eventType);
        rule.setNumberOfPoints(numberOfPoints);
        rule.setApplication(applicationId);
        
        return rule;
    }
}
