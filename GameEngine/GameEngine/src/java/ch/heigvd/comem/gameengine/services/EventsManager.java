/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Event;
import java.security.Timestamp;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Renouille
 */
@Stateless
@WebService
public class EventsManager implements EventsManagerLocal{

    @PersistenceContext
    EntityManager em;
    
    @Override
    public Long create(Long playerId, Long applicationId, String eventType, Timestamp time) {
        
        Event event = new Event();
        
        event.setPlayer(playerId);
        event.setApplication(applicationId);
        event.setEventType(eventType);
        event.setTime(time);
        em.persist(event); em.flush();
        
        return event.getEventId();
    }

}
