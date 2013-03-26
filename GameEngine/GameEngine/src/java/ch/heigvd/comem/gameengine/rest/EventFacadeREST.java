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
 *
 * @author Renouille
 */
@Stateless
@Path("events")
public class EventFacadeREST {

    @EJB
    private EventsManagerLocal eventManagerLocal;
    
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Event create(Event entity) {
        
        
        Date date = new Date();
        long time = date.getTime();
        
        Long eventId = eventManagerLocal.create(entity.getPlayer().getPlayerId(), entity.getApplication().getApiKey(), entity.getApplication().getApiSecret(), entity.getEventType(),  new Timestamp(time));
        
        return eventManagerLocal.find(eventId);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        eventManagerLocal.remove(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Event find(@PathParam("id") Long id) {
        return eventManagerLocal.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Event> findAll() {
        return eventManagerLocal.findAll();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Event> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }

//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
}
