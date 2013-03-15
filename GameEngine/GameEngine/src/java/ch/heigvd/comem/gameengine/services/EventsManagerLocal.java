/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Event;
import java.security.Timestamp;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface EventsManagerLocal {
     Long create(Long playerId, Long applicationId, String eventType, Timestamp time);
     
     Event find(Long eventId);
     
     Event update(Long eventId, Long playerId, Long applicationId, String eventType, Timestamp time);
     
     void remove(Long eventId);
}
