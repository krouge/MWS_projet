/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import ch.heigvd.comem.gameengine.model.Event;
import ch.heigvd.comem.gameengine.model.Player;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface EventsManagerLocal {
    
     Long create(Long playerId, String apiKey, String apiSecret, String eventType, Timestamp eventTime);
     
     Event find(Long eventId);
     
     List<Event> findAll();
     
     Event update(Long eventId, Player player, String apiKey, String apiSecret, String eventType, Timestamp eventTime);
     
     void remove(Long eventId);
}
