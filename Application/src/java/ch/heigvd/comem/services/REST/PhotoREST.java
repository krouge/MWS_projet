/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.services.PhotosManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author fabiencornaz
 */

@Stateless
@Path("photos")
public class PhotoREST {
    
    @EJB
    private PhotosManagerLocal photoManager;
    
    public PhotoREST() {
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Photo find(@PathParam("id") Long id) {
        
        Photo photo = null;
        try {
            photo = photoManager.find(id);
        } catch (Exception e) {
        }
        return photo;
    }
    
    
    
    
}
