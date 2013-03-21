/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdPhoto;
import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author fabiencornaz
 */
@Stateless
@WebService
public class PhotosManager implements PhotosManagerLocal {
    
    @PersistenceContext
    EntityManager em; 
    
    @Override
    public Long create(int points, String source, Utilisateur utilisateur,Theme theme){

        Photo photo = new Photo();
        photo.setPoints(points);
        photo.setSource(source);
        photo.setUtilisateur(utilisateur);

        photo.setTheme(theme);

        em.persist(photo);
        em.flush();
        utilisateur.addPhoto(photo);

        theme.addPhoto(photo);
        
        return photo.getId();
    }  
    
    public Photo find(Long idPhoto) throws ExceptionIdPhoto{
        Photo photo = em.find(Photo.class, idPhoto);
        if (photo == null) {
            throw new ExceptionIdPhoto();
        }
        return photo;        
    }
    
    public Photo update (Long idPhoto,int points, String source, Utilisateur utilisateur,Theme theme )throws ExceptionIdPhoto{
        Photo photo = em.find(Photo.class, idPhoto);
        if (photo == null) {
            throw new ExceptionIdPhoto();
        }else{
            photo.setPoints(points);
            photo.setSource(source);
            photo.setUtilisateur(utilisateur);
            photo.setTheme(theme);
            
            return photo;
        }         
    }
    
    public void associateTag(Long idPhoto, Long idTag){
        
        Photo photo = em.find(Photo.class, idPhoto);
        Tag tag = em.find(Tag.class, idTag);
        
        tag.addPhoto(photo);
        photo.addTag(tag);
        
        em.flush();
    }
    
    @Override
    public void delete(Long idPhoto) throws ExceptionIdPhoto{
        Photo photo = em.find(Photo.class, idPhoto);
        
        if (photo == null) {
            throw new ExceptionIdPhoto();
        }
        em.remove(photo);
    }
    
    public List<Photo> findAll(){
        
        Query query = em.createQuery("SELECT p FROM Photo p");
        
        return (List<Photo>)query.getResultList();
        
    }
}
