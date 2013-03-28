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
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT sur un badge
 * @author Julien Biedermann
 */
@Stateless
@Path("badges")
public class BadgeFacadeREST {

    @EJB
    private BadgesManagerLocal badgesManagerLocal;
    
    /**
     * Permet de créer une nouvelle entité badge
     * @param entity l'entité badge à créer
     * @return le badge créé
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Badge create(Badge entity) {
        Long badgeId = badgesManagerLocal.create(entity.getName(), entity.getDescription(), entity.getSource());
        
        return badgesManagerLocal.find(badgeId);
    }
    
    /**
     * Permet de modifier une entité badge
     * @param entity l'entité badge à modifier
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Badge entity) {
        badgesManagerLocal.update(entity.getBadgeId(), entity.getName(), entity.getDescription(), entity.getSource());
    }

    /**
     * Permet de supprimer une entité badge
     * @param id l'id de l'entité badge à supprimer
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        badgesManagerLocal.remove(badgesManagerLocal.find(id).getBadgeId());
    }

    /**
     * Permet de récupérer une entité badge
     * @param id l'id de l'entité badge à récupérer
     * @return l'entité badge voulue
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Badge find(@PathParam("id") Long id) {
        return badgesManagerLocal.find(id);
    }

    /**
     * Permet de récupérer toutes les entités badges
     * @return une liste des entités badges
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Badge> findAll() {
        return badgesManagerLocal.findAll();
    }
}
