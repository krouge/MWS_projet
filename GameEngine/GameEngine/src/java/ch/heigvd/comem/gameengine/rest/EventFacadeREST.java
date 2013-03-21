package ch.heigvd.comem.gameengine.rest;

import ch.heigvd.comem.gameengine.model.Event;
import ch.heigvd.comem.gameengine.services.EventsManagerLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    public void create(Event entity) {
        eventManagerLocal.create(entity.getPlayer(), entity.getApplication(), entity.getEventType(), entity.getEventTime());
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Event entity) {
        eventManagerLocal.update(entity.getEventId(), entity.getPlayer(), entity.getApplication(), entity.getEventType(), entity.getEventTime());
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
