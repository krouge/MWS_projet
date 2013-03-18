/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdTag;
import ch.heigvd.comem.model.Tag;
import javax.ejb.Local;

/**
 *
 * @author fabiencornaz
 */
@Local
public interface TagsManagerLocal {
    
    public Long create(String titre);
    public void delete(Long idTag) throws ExceptionIdTag;
    public Tag update(Long idTag, String titre) throws ExceptionIdTag;
    public Tag find (Long idTag) throws ExceptionIdTag;
    
}
