/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdTag;
import ch.heigvd.comem.exceptions.ExceptionIdTheme;
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
    
    public Long create(String titre){
        Tag tag = new Tag();
        tag.setTitre(titre);
        
        em.persist(tag);
        em.flush();
        return tag.getId();
    }
    
    public void delete(Long idTag) throws ExceptionIdTag {
        Tag tag = em.find(Tag.class,idTag);
        
        if(tag==null){
            throw new ExceptionIdTag();
        }else{
            em.remove(tag);
        }
    }
    
    public Tag update(Long idTag, String titre) throws ExceptionIdTag{
        Tag tag = em.find(Tag.class,idTag);
        
        if(tag==null){
            throw new ExceptionIdTag();
        }else{
            tag.setTitre(titre);       
            return tag;
        }
    }
    
    public Tag find (Long idTag) throws ExceptionIdTag{  
        Tag tag = em.find(Tag.class,idTag);
        
        if(tag==null){
            throw new ExceptionIdTag();
        }else{       
            return tag;
        }   
    }
}
