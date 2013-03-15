/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import javax.ejb.Local;

/**
 *
 * @author Jonas
 */
@Local
public interface ThemesManagerLocal {
    
    public Long create(String titre);
    public void delete(Long id) throws ExceptionIdTheme;
    
}
