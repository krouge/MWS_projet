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
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT sur une application
 * @author Renouille
 */
@Stateless
@Path("applications")
public class ApplicationFacadeREST {
    
    @EJB
    private ApplicationsManagerLocal appManagerLocal;
    
    /**
     * Permet de créer une nouvelle entité application
     * @param entity l'entité Application
     * @return l'application créée
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Application create(Application entity) {
        Long appId = appManagerLocal.create(entity.getName(), entity.getDescription(), entity.getApiKey(), entity.getApiSecret());
        
        return appManagerLocal.find(appId);
    }
    
    /**
     * Permet de modifier une entité application
     * @param entity l'entité à modifier
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Application entity) {
        appManagerLocal.update(entity.getApplicationId(), entity.getName(), entity.getDescription(), entity.getApiKey(), entity.getApiSecret());
    }
    
    /**
     * Permet de supprimer une entité
     * @param id l'id de l'entité application à supprimer
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        appManagerLocal.remove(id);
    }
    
    /**
     * Permet de récupérer une entité application
     * @param id l'id de l'entité application à récupérer
     * @return l'entité application voulue
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Application find(@PathParam("id") Long id) {
        return appManagerLocal.find(id);
    }
    
    /**
     * Permet de récupérer toutes les entités application
     * @return une liste d'entités application
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Application> findAll() {
        return appManagerLocal.findAll();
    }
}
