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
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT pour la gestion des tags
 * @author Jonas
 */
@Stateless
@Path("tags")
public class TagFacadeREST {
    
    @EJB
    TagsManagerLocal tagsManager;
    
    public TagFacadeREST() {
    }

    /**
     * Permet de générer une nouvelle entité tag
     * @param entity l'entité tag à créer
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Tag entity) {
        tagsManager.create(entity.getTitre());
    }

    /**
     * Permet de modifier une entité tag existante
     * @param id l'id de l'entité à modifier
     * @param entity l'entité à modifier
     * @throws ExceptionIdTag l'exception lancée si le tag n'existe pas
     */
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Tag entity) throws ExceptionIdTag {
        tagsManager.update(entity.getId(), entity.getTitre());
    }

    /**
     * Permet de supprimer une entité tag existante
     * @param id l'id du tag à supprimer
     * @throws ExceptionIdTag l'exception lancée si le tag n'existe pas
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdTag {
        tagsManager.delete(id);
    }

    /**
     * Permet de récupérer une entité tag
     * @param id l'id de l'entité tag à récupérer
     * @return le tag correspondant
     * @throws ExceptionIdTag l'exeption lancée si le tag n'existe pas
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Tag find(@PathParam("id") Long id) throws ExceptionIdTag {
        return tagsManager.find(id);
    }

    /**
     * Permet de récupérer toutes les entités tags
     * @return une liste des entités tags
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Tag> findAll() {
        return tagsManager.findAll();
    }
}
