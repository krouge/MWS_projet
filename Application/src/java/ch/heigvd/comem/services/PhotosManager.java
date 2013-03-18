/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Utilisateur;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


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
    public Long createPhoto(int points, String source, Utilisateur utilisateur){
        Photo photo = new Photo();
        photo.setPoints(points);
        photo.setSource(source);
        photo.setUtilisateur(utilisateur);
        em.persist(photo);
        em.flush();
        utilisateur.addPhoto(photo);
        return photo.getId();
    }  
    

    
    @Override
    public void remove(Long idPhoto){
        Photo photo = em.find(Photo.class, idPhoto);
        em.remove(photo);
    }
    


   
    
    
    

}
