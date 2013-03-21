/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.exceptions.ExceptionIdTag;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.services.TagsManagerLocal;
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
 * @author Jonas
 */
@Stateless
@Path("tags")
public class TagFacadeREST {
    
    @EJB
    TagsManagerLocal tagsManager;
    
    public TagFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Tag entity) {
        tagsManager.create(entity.getTitre());
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Tag entity) throws ExceptionIdTag {
        tagsManager.update(entity.getId(), entity.getTitre());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdTag {
        tagsManager.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Tag find(@PathParam("id") Long id) throws ExceptionIdTag {
        return tagsManager.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Tag> findAll() {
        return tagsManager.findAll();
    }
    /*
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Tag> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    */
    
    /*
    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    */  
}
