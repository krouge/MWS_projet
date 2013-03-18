/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Application;
import ch.heigvd.comem.gameengine.model.Event;
import ch.heigvd.comem.gameengine.model.Player;
import java.sql.Timestamp;
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
    public Long create(Application application, String eventType, Timestamp eventTime) {
        
        Event event = new Event();
       
        event.setApplication(application);
        event.setEventType(eventType);
        event.setEventTime(eventTime);
        em.persist(event); em.flush();
        
        return event.getEventId();
    }
    
    @Override
    public Event find(Long eventId) {
        
        Event event = em.find(Event.class, eventId);
        
        return event;
    }
    
    @Override
    public void remove(Long eventId) {
        Event event = em.find(Event.class, eventId);
        em.remove(event);
    }

    @Override
    public Event update(Long eventId, Player player, Application application, String eventType, Timestamp eventTime) {
        
        Event event = em.find(Event.class, eventId);
        
        event.setPlayer(player);
        event.setApplication(application);
        event.setEventType(eventType);
        event.setEventTime(eventTime);
        
        return event;
    }

}
