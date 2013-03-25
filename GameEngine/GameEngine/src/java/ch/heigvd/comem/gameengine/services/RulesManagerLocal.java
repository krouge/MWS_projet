/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.model.Rule;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface RulesManagerLocal {

    Long create(String eventType, int numberOfPoints, String apiKey, String apiSecret, Badge badge);
    
    Long create(String eventType, int numberOfPoints, String apiKey, String apiSecret);
    
    Rule find(Long ruleId);
    
    List<Rule> findAll();
    
    Rule update(Long ruleId, String eventType, int numberOfPoints, String apiKey, String apiSecret, Badge badge);
    
    Rule update(Long ruleId, String eventType, int numberOfPoints, String apiKey, String apiSecret);
    
    void remove(Long ruleId);

    void associateBadge(Long ruleId, Long badgeId);
    
}
