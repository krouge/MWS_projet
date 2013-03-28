package ch.heigvd.comem.gameengine.rest;

import ch.heigvd.comem.gameengine.model.Rule;
import ch.heigvd.comem.gameengine.services.RulesManagerLocal;
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
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT sur une règle
 * @author Renouille
 */
@Stateless
@Path("rules")
public class RuleFacadeREST {
   
    @EJB
    private RulesManagerLocal rulesManagerLocal;

    /**
     * Permet de créer une entité règle
     * @param entity l'entité règle à créer
     * @return l'entité règle créée
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Rule create(Rule entity) {
        Long ruleId = rulesManagerLocal.create(entity.getEventType(), entity.getNumberOfPoints(), entity.getApplication().getApiKey(), entity.getApplication().getApiSecret());
        
        return rulesManagerLocal.find(ruleId);
    }

    /**
     * Permet de modifier une entité règle existante
     * @param entity l'entité à modifier
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Rule entity) {
        rulesManagerLocal.update(entity.getRuleId(), entity.getEventType(), entity.getNumberOfPoints(), entity.getApplication().getApiKey(), entity.getApplication().getApiSecret());
    }
    /**
     * Permet de supprimer une entité règle
     * @param id l'id de l'entité règle à supprimer
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        rulesManagerLocal.remove(id);
    }

    /**
     * Permet de récupérer une entité règle
     * @param id l'id de l'entité règle à récupérer
     * @return 
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Rule find(@PathParam("id") Long id) {
        return rulesManagerLocal.find(id);
    }

    /**
     * Permet de récupérer toutes les entités règle
     * @return une liste des entités règle
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Rule> findAll() {
        return rulesManagerLocal.findAll();
    }
}
