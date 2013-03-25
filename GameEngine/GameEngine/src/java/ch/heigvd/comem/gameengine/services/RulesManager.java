/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.model.Rule;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Renouille
 */
@Stateless
@WebService
public class RulesManager implements RulesManagerLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private ApplicationsManagerLocal appManagerLocal;
    
    @Override
    public Long create(String eventType, int numberOfPoints, String apiKey, String apiSecret, Badge badge) {
        
       Rule rule = new Rule();
        
       rule.setEventType(eventType);
       rule.setNumberOfPoints(numberOfPoints);
       rule.setApplication(appManagerLocal.find(apiKey, apiSecret));
       rule.setBadge(badge);
       em.persist(rule); em.flush();
        
        return rule.getRuleId();
    }

    @Override
    public Long create(String eventType, int numberOfPoints, String apiKey, String apiSecret) {
        
        Rule rule = new Rule();
        
        rule.setEventType(eventType);
        rule.setNumberOfPoints(numberOfPoints);
        rule.setApplication(appManagerLocal.find(apiKey, apiSecret));
        em.persist(rule); em.flush();
        
        return rule.getRuleId();
    }
    
    @Override
    public Rule find(Long ruleId) {
        
        Rule rule = em.find(Rule.class, ruleId);
        
        return rule; 
    }
    
    @Override
    public List<Rule> findAll() {

        Query query = em.createQuery("SELECT r FROM Rule AS r");
        
        List<Rule> listRule = (List<Rule>)query.getResultList();
        
        return listRule;        
    }

    @Override
    public void remove(Long ruleId) {
        
        Rule rule = em.find(Rule.class, ruleId);
        em.remove(rule);
    }

    @Override
    public Rule update(Long ruleId, String eventType, int numberOfPoints, String apiKey, String apiSecret, Badge badge) {
        
        Rule rule = em.find(Rule.class, ruleId);
        
        rule.setEventType(eventType);
        rule.setNumberOfPoints(numberOfPoints);
        rule.setApplication(appManagerLocal.find(apiKey, apiSecret));
        rule.setBadge(badge);
        
        return rule;
    }

    @Override
    public Rule update(Long ruleId, String eventType, int numberOfPoints,String apiKey, String apiSecret) {
        
        Rule rule = em.find(Rule.class, ruleId);
        
        rule.setEventType(eventType);
        rule.setNumberOfPoints(numberOfPoints);
        rule.setApplication(appManagerLocal.find(apiKey, apiSecret));
        
        return rule;
    }

    @Override
    public void associateBadge(Long ruleId, Long badgeId) {
        
        Rule rule = em.find(Rule.class, ruleId);
        Badge badge = em.find(Badge.class, badgeId);
        
        rule.setBadge(badge);
        badge.setRule(rule);
    }
    
    
}
