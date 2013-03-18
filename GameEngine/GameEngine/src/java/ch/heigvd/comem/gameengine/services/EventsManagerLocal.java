/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import ch.heigvd.comem.gameengine.model.Event;
import ch.heigvd.comem.gameengine.model.Player;
import java.sql.Timestamp;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface EventsManagerLocal {
     Long create(Player player, Application application, String eventType, Timestamp eventTime);
     
     Event find(Long eventId);
     
     Event update(Long eventId, Player player, Application application, String eventType, Timestamp eventTime);
     
     void remove(Long eventId);
}
