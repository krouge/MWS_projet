/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Tag;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fabiencornaz
 */
@Stateless
public class TagsManager implements TagsManagerLocal {

    @PersistenceContext
    EntityManager em; 
    
    public void createTag(String titre){
        Tag tag = new Tag();
        tag.setTitre(titre);
        
        em.persist(tag);
        em.flush();
    }
    
    
    
    public void persist(Object object){
        em.persist(object);
    }

}
