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

    Long create(String eventType, int numberOfPoints, Long application, Badge badge);
    
    Long create(String eventType, int numberOfPoints, Long application);
    
    Rule find(Long ruleId);
    
    void remove(Long ruleId);
    
}
