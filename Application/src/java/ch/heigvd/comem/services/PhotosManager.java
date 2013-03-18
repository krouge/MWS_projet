/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Utilisateur;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;


/**
 *
 * @author fabiencornaz
 */
@Stateless
public class PhotosManager implements PhotosManagerLocal {
    
    @PersistenceContext
    EntityManager em; 
    
    @Override
    public Long createPhoto(int points, String source, Utilisateur ustilisateur){
        Photo photo = new Photo();
        
        photo.setPoints(points);
        photo.setSource(source);
        photo.setUtilisateur(ustilisateur);
        em.persist(photo);
        em.flush();
        return photo.getId();
    }  
    

    
    @Override
    public void remove(Long idPhoto){
        Photo photo = em.find(Photo.class, idPhoto);
        em.remove(photo);
    }
    


   
    
    
    

}
