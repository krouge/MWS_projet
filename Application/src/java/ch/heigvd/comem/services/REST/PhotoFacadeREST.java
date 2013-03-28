/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.dto.PhotoDTO;
import ch.heigvd.comem.exceptions.ExceptionIdPhoto;
import ch.heigvd.comem.exceptions.ExceptionIdTheme;
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
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT sur une photo
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
    
    /**
     * Permet de créer une nouvelle entité photo
     * @param entity l'entité photo à créer
     * @throws ExceptionIdUtilisateur l'exception si l'utilisateur lié à la photo n'existe pas
     * @throws ExceptionIdTheme l'exception si le thème lié à la photo n'existe pas
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Photo entity) throws ExceptionIdUtilisateur, ExceptionIdTheme {
        photosManager.create(entity.getTitre(),entity.getPoints(), entity.getSource(), entity.getUtilisateur().getId(), entity.getTheme().getId());
    }

    /**
     * Permet de modifier une entité photo existante
     * @param id l'id de l'utilisateur qui a créé la photo
     * @param entity l'entité photo à modifier
     * @throws ExceptionIdPhoto l'exception si la photo n'existe pas
     * @throws ExceptionIdUtilisateur l'exception si l'utilisateur lié à la photo n'existe pas
     * @throws ExceptionIdTheme l'exception si le thème lié à la photo n'existe pas
     */
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id,Photo entity) throws ExceptionIdPhoto, ExceptionIdUtilisateur, ExceptionIdTheme {
        photosManager.update(entity.getId(),entity.getTitre(),entity.getPoints(), entity.getSource(), entity.getUtilisateur().getId(), entity.getTheme().getId());
    }

    /**
     * Permet de supprimer une entité photo
     * @param id l'id de l'entité photo à supprimer
     * @throws ExceptionIdPhoto l'exception si la photo n'existe pas
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdPhoto {
        photosManager.delete(id);
    }

    /**
     * Permet de récupérer une photo avec ses paramètres (tags, utilisateur et like)
     * @param id l'id de la photo à récupérer
     * @param paramTag est égal à 1 si l'on souhaite récupérer les tags de la photo
     * @param paramUtilisateur est égal à 1 si l'on souhaite récupérer l'utilisateur lié à la photo
     * @param paramLike est égal à 1 si l'on souhaite récupérer les utilisateurs qui ont liké la photo
     * @return une PhotoDTO, une entité photo personnalisée. voir la classe PhotoDTO
     * @throws ExceptionIdPhoto 
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public PhotoDTO find(@PathParam("id") Long id, @QueryParam("tags") Long paramTag, @QueryParam("utilisateur") Long paramUtilisateur, @QueryParam("like") Long paramLike) throws ExceptionIdPhoto {
        
        Photo photo = photosManager.find(id);
        
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(photo.getId());
        photoDTO.setTitre(photo.getTitre());
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
    
    /**
     * Permet à un utilisateur de liker une photo
     * @param id l'id de la photo à liker
     * @param entity l'entité utilisateur qui like la photo
     * @throws ExceptionIdPhoto l'exeption lancée si la photo n'existe pas
     * @throws ExceptionIdUtilisateur l'exception lancée si l'utilisateur n'existe pas
     */
    @POST
    @Path("{id}/like")
    @Produces({"application/xml", "application/json"})
    public void like(@PathParam("id") Long id, Utilisateur entity) throws ExceptionIdPhoto, ExceptionIdUtilisateur{
        Photo photo = photosManager.find(id);
        Utilisateur utilisateur = utilisateursManager.find(entity.getId());
        if(!photo.getUtilisateurs().contains(utilisateur)){
            utilisateursManager.associatePhotoLike(entity.getId(), id);
        }
    }

    /**
     * Permet de récupérer toutes les entités photos avec leurs paramètres (tags, utilisateur et like)
     * @param paramTag égal 1 si l'on souhaite récupérer les tags liés aux photos
     * @param paramUser égal 1 si l'on souhaite récupérer les utilisateurs liés aux photos
     * @param paramLike égal 1 si l'on souhaite récupérer les utilisateurs qui ont liké les photos
     * @return une liste de PhotoDTO, entités de photo personnaliséés. voir classe PhotoDTO
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<PhotoDTO> findAll(@QueryParam("tags") Long paramTag, @QueryParam("utilisateur") Long paramUser, @QueryParam("like") Long paramLike) {
        
         List<Photo> listePhoto =  photosManager.findAll();
         List<PhotoDTO> listePhotoDTO = new LinkedList<PhotoDTO>();
         
         for(Photo photo : listePhoto){
             PhotoDTO photoDTO = new PhotoDTO();
             photoDTO.setId(photo.getId());
             photoDTO.setTitre(photo.getTitre());

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
}
