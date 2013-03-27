/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.dto.PhotoDTO;
import ch.heigvd.comem.dto.TagDTO;
import ch.heigvd.comem.dto.ThemeDTO;
import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.services.ThemesManagerLocal;
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
@Path("themes")
public class ThemeFacadeREST {
    
    @EJB
    ThemesManagerLocal themesManager;
    
    public ThemeFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public String create(Theme entity) throws ExceptionIdUtilisateur {
        themesManager.create(entity.getTitre(), entity.getUtilisateur().getId());
        return "aslksalkfja";
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Theme entity) throws ExceptionIdTheme {
        themesManager.update(entity.getId(), entity.getTitre());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdTheme {
        themesManager.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ThemeDTO find(@PathParam("id") Long id, @QueryParam("photos") Long withPhotos, @QueryParam("tags") Long withTags, @QueryParam("utilisateur") Long withUtilisateur, @QueryParam("tagsPhotos") Long withTagsPhotos) throws ExceptionIdTheme {
        
        Theme theme = themesManager.find(id);
        ThemeDTO themeDTO = new ThemeDTO();
        
            themeDTO.setId(theme.getId());
            themeDTO.setTitre(theme.getTitre());
        
        if(withPhotos != null && withPhotos == 1){
                List<PhotoDTO> photoDTOS = new LinkedList<PhotoDTO>();
                List<Photo> photos = theme.getPhotos();

                for(Photo photo : photos){

                    PhotoDTO photoDto = new PhotoDTO();
                    photoDto.setId(photo.getId());
                    photoDto.setPoints(photo.getPoints());
                    photoDto.setSource(photo.getSource());
                    
                    if(withTagsPhotos != null && withTagsPhotos == 1){
                        photoDto.setTags(photo.getTags());
                    }

                    photoDTOS.add(photoDto);

                }

                themeDTO.setPhotos(photoDTOS);
            }
            
            
            if(withTags != null && withTags == 1){
                
                List<TagDTO> tagDTOS = new LinkedList<TagDTO>();
                List<Tag> tags = theme.getTags();

                for(Tag tag : tags){

                    TagDTO tagDTO = new TagDTO();
                    tagDTO.setId(tag.getId());
                    tagDTO.setTitre(tag.getTitre());

                    tagDTOS.add(tagDTO);

                }

                themeDTO.setTags(tagDTOS);
                
            }
            
            if(withUtilisateur != null && withUtilisateur == 1){
                themeDTO.setUtilisateur(theme.getUtilisateur());
            }
            
        
        
        return themeDTO;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<ThemeDTO> findAll(@QueryParam("photos") Long withPhotos, @QueryParam("tags") Long withTags, @QueryParam("utilisateur") Long withUtilisateur, @QueryParam("tagsPhotos") Long withTagsPhotos) {
        
        List<Theme> themes = themesManager.findAll();
        List<ThemeDTO> themesDTO = new LinkedList<ThemeDTO>();
        
        for(Theme theme : themes){
            ThemeDTO themeDTO = new ThemeDTO();
            themeDTO.setId(theme.getId());
            themeDTO.setTitre(theme.getTitre());
            
            if(withPhotos != null && withPhotos == 1){
                List<PhotoDTO> photoDTOS = new LinkedList<PhotoDTO>();
                List<Photo> photos = theme.getPhotos();

                for(Photo photo : photos){

                    PhotoDTO photoDto = new PhotoDTO();
                    photoDto.setId(photo.getId());
                    photoDto.setPoints(photo.getPoints());
                    photoDto.setSource(photo.getSource());
                    if(withTagsPhotos != null && withTagsPhotos == 1){
                        photoDto.setTags(photo.getTags());
                    }

                    photoDTOS.add(photoDto);

                }

                themeDTO.setPhotos(photoDTOS);
            }
            
            
            if(withTags != null && withTags == 1){
                
                List<TagDTO> tagDTOS = new LinkedList<TagDTO>();
                List<Tag> tags = theme.getTags();

                for(Tag tag : tags){

                    TagDTO tagDTO = new TagDTO();
                    tagDTO.setId(tag.getId());
                    tagDTO.setTitre(tag.getTitre());

                    tagDTOS.add(tagDTO);

                }

                themeDTO.setTags(tagDTOS);
                
            }
            
            if(withUtilisateur != null && withUtilisateur == 1){
                themeDTO.setUtilisateur(theme.getUtilisateur());
            }
            
            themesDTO.add(themeDTO);
            
        }
        
        
        return themesDTO;
    }
    
    @GET
    @Path("last20")
    @Produces({"application/xml", "application/json"})
    public List<ThemeDTO> findLast20(@QueryParam("photos") Long withPhotos, @QueryParam("tags") Long withTags, @QueryParam("utilisateur") Long withUtilisateur) {
        
        List<Theme> themes = themesManager.findLast20();
        List<ThemeDTO> themesDTO = new LinkedList<ThemeDTO>();
        
        for(Theme theme : themes){
            ThemeDTO themeDTO = new ThemeDTO();
            themeDTO.setId(theme.getId());
            themeDTO.setTitre(theme.getTitre());
            
            if(withPhotos != null && withPhotos == 1){
                List<PhotoDTO> photoDTOS = new LinkedList<PhotoDTO>();
                List<Photo> photos = theme.getPhotos();

                for(Photo photo : photos){

                    PhotoDTO photoDto = new PhotoDTO();
                    photoDto.setPoints(photo.getPoints());
                    photoDto.setSource(photo.getSource());

                    photoDTOS.add(photoDto);

                }

                themeDTO.setPhotos(photoDTOS);
            }
            
            
            if(withTags != null && withTags == 1){
                
                List<TagDTO> tagDTOS = new LinkedList<TagDTO>();
                List<Tag> tags = theme.getTags();

                for(Tag tag : tags){

                    TagDTO tagDTO = new TagDTO();
                    tagDTO.setTitre(tag.getTitre());

                    tagDTOS.add(tagDTO);

                }

                themeDTO.setTags(tagDTOS);
                
            }
            
            if(withUtilisateur != null && withUtilisateur == 1){
                themeDTO.setUtilisateur(theme.getUtilisateur());
            }
            
            themesDTO.add(themeDTO);
            
        }
        
        
        return themesDTO;
    }
    
    
    @GET
    @Path("search")
    @Produces({"application/xml", "application/json"})
    public List<ThemeDTO> findByName(@QueryParam("photos") Long withPhotos, @QueryParam("tags") Long withTags, @QueryParam("utilisateur") Long withUtilisateur, @QueryParam("search") String search) {
        
        List<Theme> themes = themesManager.findByName(search);
        List<ThemeDTO> themesDTO = new LinkedList<ThemeDTO>();
        
        for(Theme theme : themes){
            ThemeDTO themeDTO = new ThemeDTO();
            themeDTO.setId(theme.getId());
            themeDTO.setTitre(theme.getTitre());
            
            if(withPhotos != null && withPhotos == 1){
                List<PhotoDTO> photoDTOS = new LinkedList<PhotoDTO>();
                List<Photo> photos = theme.getPhotos();

                for(Photo photo : photos){

                    PhotoDTO photoDto = new PhotoDTO();
                    photoDto.setPoints(photo.getPoints());
                    photoDto.setSource(photo.getSource());

                    photoDTOS.add(photoDto);

                }

                themeDTO.setPhotos(photoDTOS);
            }
            
            
            if(withTags != null && withTags == 1){
                
                List<TagDTO> tagDTOS = new LinkedList<TagDTO>();
                List<Tag> tags = theme.getTags();

                for(Tag tag : tags){

                    TagDTO tagDTO = new TagDTO();
                    tagDTO.setTitre(tag.getTitre());

                    tagDTOS.add(tagDTO);

                }

                themeDTO.setTags(tagDTOS);
                
            }
            
            if(withUtilisateur != null && withUtilisateur == 1){
                themeDTO.setUtilisateur(theme.getUtilisateur());
            }
            
            themesDTO.add(themeDTO);
            
        }
        
        
        return themesDTO;
    }
    
    /*
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Theme> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    */
    
}
