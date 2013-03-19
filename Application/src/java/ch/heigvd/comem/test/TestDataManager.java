/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.test;

import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import ch.heigvd.comem.services.PhotosManagerLocal;
import ch.heigvd.comem.services.ThemesManagerLocal;
import ch.heigvd.comem.services.UtilisateursManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author Jonas
 */
@Stateless
public class TestDataManager implements TestDataManagerLocal {
    
    @EJB
    private UtilisateursManagerLocal utilisateurManager;
    
    @EJB
    private PhotosManagerLocal photoManager;
    
    @EJB
    private ThemesManagerLocal themeManager;
    
    public void generateTestData(){
        Utilisateur utilisateur = null;
        Theme theme = null;
        Long id = utilisateurManager.create("theplayer777", "jonas@jonas.ch", "1234");
        
        try{
            utilisateur = utilisateurManager.find(id);
            try {
                Long idTheme = themeManager.create("Theme",utilisateur);
                theme = themeManager.find(idTheme);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch(Exception e){
        }
        photoManager.create(10, "http://comem.ch", utilisateur, theme);
        
        
    }

}
