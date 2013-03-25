/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jonas
 */
@Stateless
public class ThemesManager implements ThemesManagerLocal {

    @PersistenceContext
    private EntityManager em;

    
    public Long create(String titreTheme, Utilisateur utilisateur){
        Theme theme = new Theme();
        
        Query query = em.createQuery("SELECT t FROM Theme t WHERE t.titre LIKE :titre");
        query.setParameter("titre", titreTheme);
         
        if (query.getResultList().isEmpty()) {
             theme.setTitre(titreTheme);
             theme.setUtilisateur(utilisateur);
             utilisateur.addTheme(theme);
             em.persist(theme);
             em.flush();
            
            return theme.getId();
        }else{
            Theme themeExistant = (Theme) query.getSingleResult();
       
            return themeExistant.getId();
        }
        
    }

    public void delete(Long id) throws ExceptionIdTheme {
        Theme theme = em.find(Theme.class, id);
        
        if(theme != null){
            em.remove(theme);
        }else{
            throw new ExceptionIdTheme();
        }
    }
    
    public Theme find(Long id) throws ExceptionIdTheme{
        Theme theme = em.find(Theme.class, id);
        
        if(theme == null){
            throw new ExceptionIdTheme();
        }
        return theme;
    }
    
    public Theme update(Long id, String titre) throws ExceptionIdTheme{
        Theme theme = em.find(Theme.class, id);
        theme.setTitre(titre);
        return theme;
    }
    
    public void associateTag(Long idTheme, Long idTag){
        Theme theme = em.find(Theme.class, idTheme);
        Tag tag = em.find(Tag.class, idTag);
        theme.addTag(tag);
        tag.addTheme(theme);
        em.flush();
    }
    
    public List<Theme> findAll(){
        
        Query query = em.createQuery("SELECT t FROM Theme t");
        
        return (List<Theme>)query.getResultList();
    }
    
    public List<Theme> findLast20(){
        Query query = em.createQuery("SELECT t FROM Theme t ORDER BY t.id");
        query.setFirstResult(0);
        query.setMaxResults(20);
        
        return query.getResultList();
    }



}
