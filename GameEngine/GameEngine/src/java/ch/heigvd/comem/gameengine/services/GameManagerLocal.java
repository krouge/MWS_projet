/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Player;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface GameManagerLocal {

    void notifyEvent(long playerId, long eventId);

    void checkTotalOfPoints(Player player);
    
}
