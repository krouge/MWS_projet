/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jonas
 */
@Stateless
public class ThemesManager implements ThemesManagerLocal {

    @PersistenceContext
    private EntityManager em;

    
    public Long create(String titre, Utilisateur utilisateur){
        Theme theme = new Theme();
        theme.setTitre(titre);
        theme.setUtilisateur(utilisateur);
        utilisateur.addTheme(theme);
        em.persist(theme);
        em.flush();
        theme.setUtilisateur(utilisateur);
        //utilisateur.addTheme(theme);
        return theme.getId();
        
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


}
