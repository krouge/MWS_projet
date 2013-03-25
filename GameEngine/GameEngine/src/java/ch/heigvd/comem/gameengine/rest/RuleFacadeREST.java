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
 *
 * @author Renouille
 */
@Stateless
@Path("rules")
public class RuleFacadeREST {
   
    @EJB
    private RulesManagerLocal rulesManagerLocal;

    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Rule create(Rule entity) {
        Long ruleId = rulesManagerLocal.create(entity.getEventType(), entity.getNumberOfPoints(), entity.getApplication());
        
        return rulesManagerLocal.find(ruleId);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Rule entity) {
        rulesManagerLocal.update(entity.getRuleId(), entity.getEventType(), entity.getNumberOfPoints(), entity.getApplication());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        rulesManagerLocal.remove(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Rule find(@PathParam("id") Long id) {
        return rulesManagerLocal.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Rule> findAll() {
        return rulesManagerLocal.findAll();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Rule> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
