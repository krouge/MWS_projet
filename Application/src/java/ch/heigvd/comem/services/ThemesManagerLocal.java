/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import javax.ejb.Local;

/**
 *
 * @author Jonas
 */
@Local
public interface ThemesManagerLocal {
    
    public Long createTheme(String titre);
    
}
