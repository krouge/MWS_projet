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
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT pour la gestion de themes
 * @author Jonas
 */
@Stateless
@Path("themes")
public class ThemeFacadeREST {
    
    @EJB
    ThemesManagerLocal themesManager;
    
    public ThemeFacadeREST() {
    }

    /**
     * Permet de créer une nouvelle entité thème
     * @param entity l'entité thème à créer
     * @return l'entité thème créée
     * @throws ExceptionIdUtilisateur l'exception lancée si l'utilisateur n'existe pas
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public String create(Theme entity) throws ExceptionIdUtilisateur {
        themesManager.create(entity.getTitre(), entity.getUtilisateur().getId());
        return "aslksalkfja";
    }

    /**
     * Permet de modifier une entité thème existante
     * @param id l'id de l'entité thème à modifier
     * @param entity l'entità thème à modifier
     * @throws ExceptionIdTheme l'exception lancée si le thème n'existe pas
     */
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Theme entity) throws ExceptionIdTheme {
        themesManager.update(entity.getId(), entity.getTitre());
    }

    /**
     * Permet de supprimer une entité thème
     * @param id l'id de l'entité à supprimer
     * @throws ExceptionIdTheme l'exception lancée si le thème n'existe pas
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdTheme {
        themesManager.delete(id);
    }

    /**
     * Permet de récupérer une entité thème avec ses paramètres (photos, tags, utilisateur et les tags des photos du thème)
     * @param id l'id de l'entité à récupérer
     * @param withPhotos égal 1 si l'on souhaite récupérer les photos liées au thème
     * @param withTags égal 1 si l'on souhaite récupérer les tags liés au thème
     * @param withUtilisateur égal 1 si l'on souhaite récupérer l'utilisateur qui a créé le thème
     * @param withTagsPhotos égal 1 si l'on souhaite récupérer les tags des photos liées au thème
     * @return ThemeDTO, une entité Theme personnalisée. voir classe ThemeDTO
     * @throws ExceptionIdTheme l'exception lancée si le thème n'existe pas
     */
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

    /**
     * Permet de récupérer toutes les entités thèmes avec leurs paramètres (photos, tags, utilisateur et les tags des photos du thème)
     * @param withPhotos égal 1 si l'on souhaite récupérer les photos liées aux thèmes
     * @param withTags égal 1 si l'on souhaite récupérer les tags liés aux thèmes
     * @param withUtilisateur égal 1 si l'on souhaite récupérer les utilisateurs qui ont créé les thèmes
     * @param withTagsPhotos égal 1 si l'on souhaite récupérer les tags des photos liées aux thèmes
     * @return une liste de ThemeDTO, des entités Theme personnalisées. voir classe ThemeDTO
     */
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

    /**
     * Permet de récupérer les 20 dernières entités thèmes créées avec leurs paramètres (photos, tags et utilisateurs)
     * @param withPhotos égal 1 si l'on souhaite récupérer les photos liées aux thèmes
     * @param withTags égal 1 si l'on souhaite récupérer les tags liés aux thèmes
     * @param withUtilisateur égal 1 si l'on souhaite récupérer les utilisateurs qui ont créé les thèmes
     * @return une liste de ThemeDTO, des entités Theme personnalisées. voir classe ThemeDTO
     */
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
    
    /**
     * Permet de rechercher des thpmes par leurs titres avec leurs paramètres (photos, tags et utilisateur)
     * @param withPhotos égal 1 si l'on souhaite récupérer les photos liées aux thèmes
     * @param withTags égal 1 si l'on souhaite récupérer les tags liés aux thèmes
     * @param withUtilisateur égal 1 si l'on souhaite récupérer les utilisateurs qui ont créé les thèmes
     * @param search la recherche effectuée
     * @return une liste de ThemeDTO, des entités Theme personnalisées. voir classe ThemeDTO
     */
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
}
