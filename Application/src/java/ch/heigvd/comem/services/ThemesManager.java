/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.model.Theme;
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

    
    public Long createTheme(String titre){
        Theme theme = new Theme();
        theme.setTitre(titre);
        em.persist(theme);
        em.flush();
        return theme.getId();
        
    }

    public void persist(Object object) {
        em.persist(object);
    }


}
