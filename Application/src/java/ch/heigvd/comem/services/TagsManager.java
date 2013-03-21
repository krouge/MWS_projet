/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdTag;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fabiencornaz
 */
@Stateless
public class TagsManager implements TagsManagerLocal {

    @PersistenceContext
    EntityManager em; 
    
    public Long create(String titreTag){
        Tag tag = new Tag();
        
        Query query = em.createQuery("SELECT t FROM Tag t WHERE t.titre LIKE :titre");
        query.setParameter("titre", titreTag);
         
        if (query.getResultList().isEmpty()) {
            tag.setTitre(titreTag);
            em.persist(tag);
            em.flush();
            
            return tag.getId();
        }else{
            Tag tagExistant = (Tag) query.getSingleResult();
            
            return tagExistant.getId();
        }
        
            
        
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
    
    public List<Tag> findAll(){
        Query query = em.createQuery("SELECT t FROM Tag t");
        
        return (List<Tag>)query.getResultList();
    }
}
