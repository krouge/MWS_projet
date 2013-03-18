/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import javax.ejb.Local;

/**
 *
 * @author Jonas
 */
@Local
public interface ThemesManagerLocal {
    
    public Long create(String titre);
    public void delete(Long id) throws ExceptionIdTheme;
    public Theme find(Long id) throws ExceptionIdTheme; 
    public Theme update(Long id, String titre);  
    public void associateTag(Long idTheme, Long idTag);
    
}
