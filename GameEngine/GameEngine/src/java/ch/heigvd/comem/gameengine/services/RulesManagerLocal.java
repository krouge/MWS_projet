/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface RulesManagerLocal {

    Long create(String eventType, int numberOfPoints, Badge badge);
    
}
