/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Event;
import ch.heigvd.comem.gameengine.model.Player;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Renouille
 */
@Stateless
@WebService
public class EventsManager implements EventsManagerLocal{

    @PersistenceContext
    EntityManager em;
    @EJB
    ApplicationsManagerLocal applicationsManagerLocal;
    
    @EJB
    PlayersManagerLocal playersManagerLocal;
    
    @EJB GameManagerLocal gameManagerLocal;
    
    @Override
    public Long create(Long playerId, String apiKey, String apiSecret, String eventType, Timestamp eventTime) {
        
        Player player = playersManagerLocal.find(playerId);
        Event event = new Event();
       
        event.setPlayer(player);
        event.setApplication(applicationsManagerLocal.find(apiKey, apiSecret));
        event.setEventType(eventType);
        event.setEventTime(eventTime);
        player.addEvent(event);
        em.persist(event); em.flush();
        
        gameManagerLocal.notifyEvent(playerId, event.getEventId());

        return event.getEventId();
    }
    
    @Override
    public Event find(Long eventId) {
        
        Event event = em.find(Event.class, eventId);
        
        return event;
    }
    
    @Override
    public List<Event> findAll() {
        
        Query query = em.createQuery("SELECT e FROM Event AS e");
        
        List<Event> listEvent = (List<Event>)query.getResultList();
        
        return listEvent;  
    }
    
    @Override
    public void remove(Long eventId) {
        Event event = em.find(Event.class, eventId);
        em.remove(event);
    }

    @Override
    public Event update(Long eventId, Player player, String apiKey, String apiSecret, String eventType, Timestamp eventTime) {
        
        Event event = em.find(Event.class, eventId);
        
        event.setPlayer(player);
        player.getEvents().remove(event);
        player.addEvent(event);
        event.setApplication(applicationsManagerLocal.find(apiKey, apiSecret));
        event.setEventType(eventType);
        event.setEventTime(eventTime);
        
        return event;
    }

}
