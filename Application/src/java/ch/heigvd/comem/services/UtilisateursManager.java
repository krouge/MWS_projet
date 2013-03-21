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
import javax.persistence.Query;

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
    
    public List<Utilisateur> findAll(){
        Query query = em.createQuery("SELECT u FROM Utilisateur u");
        
        return (List<Utilisateur>)query.getResultList();
    }
    
    public Utilisateur update(Long id, String pseudo, String email, String mdp) throws ExceptionIdUtilisateur{
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        utilisateur.setEmail(email);
        utilisateur.setPseudo(pseudo);
        utilisateur.setMdp(mdp);
        return utilisateur;
    }
    
    public void associatePhotoLike(Long id, Long idPhoto){
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        Photo photo = em.find(Photo.class, idPhoto);
        utilisateur.addPhotoLike(photo);
        photo.addUtilisateurLike(utilisateur);
        em.flush();
    }
    
     public String login(String pseudoUser, String mdpUser){
         

         Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.pseudo LIKE :pseudo AND u.mdp LIKE :mdp");
         query.setParameter("pseudo", pseudoUser);
         query.setParameter("mdp", mdpUser);
         
         
         if (query.getResultList().isEmpty()) {

            return "DEGAGE  "+mdpUser+pseudoUser;
        }else{
            Utilisateur utilisateurExistant = (Utilisateur) query.getSingleResult();
       
            return "BRAVO";
        }
                  
     }
    

}
