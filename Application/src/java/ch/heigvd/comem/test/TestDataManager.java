/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.test;

import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import ch.heigvd.comem.services.PhotosManagerLocal;
import ch.heigvd.comem.services.TagsManagerLocal;
import ch.heigvd.comem.services.ThemesManagerLocal;
import ch.heigvd.comem.services.UtilisateursManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

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
    
    @EJB
    private TagsManagerLocal tagManager;
    
    public void generateTestData(){
        Utilisateur utilisateur = null;
        Theme theme = null;
        Tag tag = null;
        Long id = utilisateurManager.create("theplayer777", "jonas@jonas.ch", "1234");
        
        
        Long idTag = tagManager.create("paysage");
        
       
        Long idTheme = 0L;
        
        try{
            utilisateur = utilisateurManager.find(id);
            try {
                idTheme = themeManager.create("Theme",utilisateur);
                theme = themeManager.find(idTheme);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch(Exception e){
        }
        Long idPhoto = photoManager.create(10, "http://comem.ch", utilisateur, theme);
        
        photoManager.associateTag(idPhoto, idTag);
        themeManager.associateTag(idTheme, idTag);
        
    }

}
