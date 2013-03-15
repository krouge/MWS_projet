/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.model.Rule;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface RulesManagerLocal {

    Long create(String eventType, int numberOfPoints, Long applicationId, Long badgeId);
    
    Long create(String eventType, int numberOfPoints, Long applicationId);
    
    Rule find(Long ruleId);
    
    Rule update(Long ruleId, String eventType, int numberOfPoints, Long applicationId, Long badgeId);
    
    Rule updage(Long ruleId, String eventType, int numberOfPoints, Long applicationId);
    
    void remove(Long ruleId);
    
}
