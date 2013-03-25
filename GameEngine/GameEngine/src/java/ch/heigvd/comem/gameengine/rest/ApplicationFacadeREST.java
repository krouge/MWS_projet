package ch.heigvd.comem.gameengine.rest;

import ch.heigvd.comem.gameengine.model.Application;
import ch.heigvd.comem.gameengine.services.ApplicationsManagerLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@Path("applications")
public class ApplicationFacadeREST {
    
    @EJB
    private ApplicationsManagerLocal appManagerLocal;
    
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Application create(Application entity) {
        Long appId = appManagerLocal.create(entity.getName(), entity.getDescription(), entity.getApiKey(), entity.getApiSecret());
        
        return appManagerLocal.find(appId);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Application entity) {
        appManagerLocal.update(entity.getApplicationId(), entity.getName(), entity.getDescription(), entity.getApiKey(), entity.getApiSecret());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        appManagerLocal.remove(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Application find(@PathParam("id") Long id) {
        return appManagerLocal.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Application> findAll() {
        return appManagerLocal.findAll();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Application> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
}
