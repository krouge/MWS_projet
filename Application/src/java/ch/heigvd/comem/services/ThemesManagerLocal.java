/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jonas
 */
@Local
public interface ThemesManagerLocal {
    
    public Long create(String titreTheme, Long utilisateurId) throws ExceptionIdUtilisateur;  
    public void delete(Long id) throws ExceptionIdTheme;
    public Theme find(Long id) throws ExceptionIdTheme; 
    public Theme update(Long id, String titre) throws ExceptionIdTheme;  
    public void associateTag(Long idTheme, Long idTag);
    public List<Theme> findAll();
    public List<Theme> findLast20();
    
}
