/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.model.Rule;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface RulesManagerLocal {

    Long create(String eventType, int numberOfPoints, Application application, Badge badge);
    
    Long create(String eventType, int numberOfPoints, Application application);
    
    Rule find(Long ruleId);
    
    Rule update(Long ruleId, String eventType, int numberOfPoints, Application application, Badge badge);
    
    Rule update(Long ruleId, String eventType, int numberOfPoints, Application application);
    
    void remove(Long ruleId);

    void associateBadge(Long ruleId, Long badgeId);
    
}
