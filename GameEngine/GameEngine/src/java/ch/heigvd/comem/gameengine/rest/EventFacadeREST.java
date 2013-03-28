package ch.heigvd.comem.gameengine.rest;

import ch.heigvd.comem.gameengine.model.Event;
import ch.heigvd.comem.gameengine.services.EventsManagerLocal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT sur un événement
 * @author Renouille
 */
@Stateless
@Path("events")
public class EventFacadeREST {

    @EJB
    private EventsManagerLocal eventManagerLocal;
    
    /**
     * Permet de créer une nouvelle entité événement
     * @param entity l'entité événement à créer
     * @return l'entité événement créée
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Event create(Event entity) {
        
        
        Date date = new Date();
        long time = date.getTime();
        
        Long eventId = eventManagerLocal.create(entity.getPlayer().getPlayerId(), entity.getApplication().getApiKey(), entity.getApplication().getApiSecret(), entity.getEventType(),  new Timestamp(time));
        
        return eventManagerLocal.find(eventId);
    }

    /**
     * Permet de supprimer une entité événement
     * @param id l'id de l'entité événement à supprimer
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        eventManagerLocal.remove(id);
    }

    /**
     * Permet de récupérer une entité événement
     * @param id l'id de l'entité événement à récupérer
     * @return l'entité événement à récupérer
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Event find(@PathParam("id") Long id) {
        return eventManagerLocal.find(id);
    }

    /**
     * Permet de récupérer toutes les entités événements
     * @return une liste d'entités événements
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Event> findAll() {
        return eventManagerLocal.findAll();
    }
}
