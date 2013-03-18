/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jonas
 */
@Stateless
@WebService
public class UtilisateursManager implements UtilisateursManagerLocal {

    @PersistenceContext
    private EntityManager em;

    
    public Long create(String pseudo, String email, String mdp){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(pseudo);
        utilisateur.setEmail(email);
        utilisateur.setMdp(mdp);
        em.persist(utilisateur);
        em.flush();
        return utilisateur.getId();
        
    }

    public void delete(Long id) throws ExceptionIdUtilisateur {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        
        if(utilisateur != null){
            em.remove(utilisateur);
        }else{
            throw new ExceptionIdUtilisateur();
        }
    }
    
    public Utilisateur find(Long id) throws ExceptionIdUtilisateur{
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        
        if(utilisateur == null){
            throw new ExceptionIdUtilisateur();
        }
        return utilisateur;
    }
    
    public void update(Long id, String pseudo, String email, String mdp, List<Photo> photos, List<Theme> themes) throws ExceptionIdUtilisateur{
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        utilisateur.setEmail(email);
        utilisateur.setPseudo(pseudo);
        utilisateur.setMdp(mdp);
        utilisateur.setPhotos(photos);
        utilisateur.setThemes(themes);
    }
    
    public void updatePhotoLike(Long id, Photo photo){
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        utilisateur.addPhotoLike(photo);
        photo.addUtilisateurLike(utilisateur);
        em.flush();
    }

}
