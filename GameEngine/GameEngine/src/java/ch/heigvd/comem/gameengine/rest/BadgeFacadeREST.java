package ch.heigvd.comem.gameengine.rest;

import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.services.BadgesManagerLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
 * @author Julien Biedermann
 */
@Stateless
@Path("badges")
public class BadgeFacadeREST {

    @EJB
    private BadgesManagerLocal badgesManagerLocal;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Badge entity) {
        badgesManagerLocal.create(entity.getName(), entity.getDescription(), entity.getSource());
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Badge entity) {
        badgesManagerLocal.update(entity.getBadgeId(), entity.getName(), entity.getDescription(), entity.getSource());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        badgesManagerLocal.delete(badgesManagerLocal.find(id).getBadgeId());
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Badge find(@PathParam("id") Long id) {
        return badgesManagerLocal.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Badge> findAll() {
        return badgesManagerLocal.findAll();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Badge> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }

//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
    
}
