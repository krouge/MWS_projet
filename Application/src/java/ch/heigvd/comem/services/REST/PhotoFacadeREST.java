/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.dto.PhotoDTO;
import ch.heigvd.comem.exceptions.ExceptionIdPhoto;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Utilisateur;
import ch.heigvd.comem.services.PhotosManagerLocal;
import ch.heigvd.comem.services.UtilisateursManagerLocal;
import com.sun.jersey.multipart.MultiPart;
import java.util.LinkedList;
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
import javax.ws.rs.QueryParam;

/**
 *
 * @author Jonas
 */
@Stateless
@Path("photos")
public class PhotoFacadeREST {

    @EJB
    private PhotosManagerLocal photosManager;
    
    @EJB
    private UtilisateursManagerLocal utilisateursManager;

    public PhotoFacadeREST() {
        
    }
    
    @POST
    @Consumes({"application/xml", "application/json", "multipart/mixed"})
    public String create(MultiPart multipart) throws ExceptionIdUtilisateur {
        System.out.println("LOL");
        return "it works";
//        BodyPartEntity bpe = (BodyPartEntity) multipart.getEntity();
//        String id = UUID.randomUUID().toString();
//        boolean isProcessed = false;
//        String message = null;
//        try {
//          InputStream source = bpe.getInputStream();
//          BufferedImage bi = ImageIO.read(source);
//
//          File file = new File("C:/Users/Renouille/Desktop/image.png");
//
//          //storing the image to file system.
//          if (file.isDirectory()) {
//            ImageIO.write(bi, "png", file);
//          } else {
//            file.mkdirs();
//            ImageIO.write(bi, "png", file);
//          }
//          isProcessed = true;
//
//        } catch (Exception e) {
//          message = e.getMessage();
//        }
//        
//        photosManager.create(entity.getPoints(), entity.getSource(), entity.getUtilisateur().getId(), entity.getTheme());
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id,Photo entity) throws ExceptionIdPhoto {
        photosManager.update(entity.getId(),entity.getPoints(), entity.getSource(), entity.getUtilisateur(), entity.getTheme());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdPhoto {
        photosManager.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public PhotoDTO find(@PathParam("id") Long id, @QueryParam("tags") Long paramTag, @QueryParam("utilisateur") Long paramUtilisateur, @QueryParam("like") Long paramLike) throws ExceptionIdPhoto {
        
        Photo photo = photosManager.find(id);
        
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(photo.getId());
        photoDTO.setPoints(photo.getPoints());
        photoDTO.setSource(photo.getSource());
        
        
        if (paramUtilisateur != null && paramUtilisateur == 1) {
            photoDTO.setUtilisateur(photo.getUtilisateur());
        }
        
        if (paramTag != null && paramTag == 1) {
            photoDTO.setTags(photo.getTags());
        }
         
        if (paramLike != null && paramLike == 1) {
            photoDTO.setUtilisateurs(photo.getUtilisateurs());
        }
        
        return photoDTO;
    }
    
    @POST
    @Path("{id}/like")
    @Produces({"application/xml", "application/json"})
    public void like(@PathParam("id") Long id, Utilisateur entity){
        utilisateursManager.associatePhotoLike(id, entity.getId());
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<PhotoDTO> findAll(@QueryParam("tags") Long paramTag, @QueryParam("utilisateur") Long paramUser, @QueryParam("like") Long paramLike) {
        
         List<Photo> listePhoto =  photosManager.findAll();
         List<PhotoDTO> listePhotoDTO = new LinkedList<PhotoDTO>();
         
         for(Photo photo : listePhoto){
             PhotoDTO photoDTO = new PhotoDTO();
             photoDTO.setId(photo.getId());
             photoDTO.setPoints(photo.getPoints());
             photoDTO.setSource(photo.getSource());
             
             
             if (paramUser != null && paramUser == 1) {
                 photoDTO.setUtilisateur(photo.getUtilisateur());
             }
        
             if (paramTag != null && paramTag == 1) {
                 photoDTO.setTags(photo.getTags());
             }
             
             if (paramLike != null && paramLike == 1) {
                 photoDTO.setUtilisateurs(photo.getUtilisateurs());
             }
             
             listePhotoDTO.add(photoDTO);
         }
         
         
         return listePhotoDTO;
    }
    
    /*
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Photo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    */
}
