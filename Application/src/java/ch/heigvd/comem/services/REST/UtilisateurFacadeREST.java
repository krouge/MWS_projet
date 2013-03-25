/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.dto.PhotoDTO;
import ch.heigvd.comem.dto.ThemeDTO;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Utilisateur;
import ch.heigvd.comem.dto.UtilisateurDTO;
import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.services.UtilisateursManagerLocal;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
@Path("utilisateurs")
public class UtilisateurFacadeREST{
    
    @EJB
    private UtilisateursManagerLocal utilisateurManager; 

    public UtilisateurFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Utilisateur entity) {
        utilisateurManager.create(entity.getPseudo(), entity.getEmail(), entity.getMdp());
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Utilisateur entity) throws ExceptionIdUtilisateur {
            utilisateurManager.update(entity.getId(),entity.getPseudo(), entity.getEmail(), entity.getMdp());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdUtilisateur { 
        utilisateurManager.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public UtilisateurDTO find(@PathParam("id") Long id, @QueryParam("themes") Long withThemes, @QueryParam("photos") Long withPhotos, @QueryParam("like") Long withLike) throws ExceptionIdUtilisateur {

        Utilisateur utilisateur = utilisateurManager.find(id);
        
        
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        
        utilisateurDTO.setId(utilisateur.getId());
        utilisateurDTO.setEmail(utilisateur.getEmail());
        utilisateurDTO.setMdp(utilisateur.getMdp());
        utilisateurDTO.setPseudo(utilisateur.getPseudo());
        utilisateurDTO.setIdPlayer(utilisateur.getIdPlayer());
        
       
        if(withThemes != null && withThemes == 1){
            List<ThemeDTO> themeDTOS = new LinkedList<ThemeDTO>();
            List<Theme> themes = utilisateur.getThemes();

            for(Theme theme : themes){

                ThemeDTO themeDTO = new ThemeDTO();
                themeDTO.setTitre(theme.getTitre());

                themeDTOS.add(themeDTO);

            }

            utilisateurDTO.setThemes(themeDTOS);
        }
        
        if(withPhotos != null && withPhotos == 1){
            
            List<PhotoDTO> photoDTOS = new LinkedList<PhotoDTO>();
            List<Photo> photos = utilisateur.getPhotos();
            
            for(Photo photo : photos){
                
                PhotoDTO photoDto = new PhotoDTO();
                photoDto.setPoints(photo.getPoints());
                photoDto.setSource(photo.getSource());
                
                photoDTOS.add(photoDto);
                
            }
            
            utilisateurDTO.setPhotos(photoDTOS);
            
        }
        
        /*
        if(withLike == null && withLike == 1){
            utilisateurDTO.setPhotos_like(utilisateur.getPhotos_like());
        }
        */
        
        return utilisateurDTO;
    }
    
    
    @POST
    @Path("login")
    @Consumes({"application/xml", "application/json"})
    @Produces("application/json")
    public String login(Utilisateur entity) {

        return utilisateurManager.login(entity.getPseudo(),entity.getMdp());
    }
    
    
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List<UtilisateurDTO> findAll(@QueryParam("themes") Long withThemes, @QueryParam("photos") Long withPhotos, @QueryParam("like") Long withLike) {
        
        List<Utilisateur> utilisateurs = utilisateurManager.findAll();
        List<UtilisateurDTO> utilisateursDTO = new LinkedList<UtilisateurDTO>();
        
        for(Utilisateur utilisateur : utilisateurs){
            UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
            utilisateurDTO.setId(utilisateur.getId());
            utilisateurDTO.setEmail(utilisateur.getEmail());
            utilisateurDTO.setMdp(utilisateur.getMdp());
            utilisateurDTO.setPseudo(utilisateur.getPseudo());
            utilisateurDTO.setIdPlayer(utilisateur.getIdPlayer());
            
            if(withThemes != null && withThemes == 1){
                
                List<ThemeDTO> themeDTOS = new LinkedList<ThemeDTO>();
                List<Theme> themes = utilisateur.getThemes();

                for(Theme theme : themes){

                    ThemeDTO themeDTO = new ThemeDTO();
                    themeDTO.setTitre(theme.getTitre());

                    themeDTOS.add(themeDTO);

                }

                utilisateurDTO.setThemes(themeDTOS);
            }
        
            if(withPhotos != null && withPhotos == 1){
                
                List<PhotoDTO> photoDTOS = new LinkedList<PhotoDTO>();
                List<Photo> photos = utilisateur.getPhotos();

                for(Photo photo : photos){

                    PhotoDTO photoDto = new PhotoDTO();
                    photoDto.setPoints(photo.getPoints());
                    photoDto.setSource(photo.getSource());

                    photoDTOS.add(photoDto);

                }

                utilisateurDTO.setPhotos(photoDTOS);
                }
            
            /*
            if(withLike == null && withLike == 1){
                utilisateurDTO.setPhotos_like(utilisateur.getPhotos_like());
            }s
            
            */
            utilisateursDTO.add(utilisateurDTO);
        }
       
        return utilisateursDTO;
    }

    /**
    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    */
    
}
