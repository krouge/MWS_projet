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
        /*
        Utilisateur utilisateur = null;
        Theme theme = null;
        Tag tag = null;
        
        Long id = utilisateurManager.create("theplayer777", "jonas@jonas.ch", "1234");
        
        
        Long idTag = tagManager.create("paysage");
        
       
        Long idTheme = 0L;
        Long idTheme2 = 0L;
        
        try{
            utilisateur = utilisateurManager.find(id);
            try {
                idTheme = themeManager.create("Theme",utilisateur);
                idTheme2 = themeManager.create("Themes",utilisateur);
                theme = themeManager.find(idTheme);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch(Exception e){
        }
        Long idPhoto = photoManager.create(10, "http://comem.ch", utilisateur, theme);
        
        photoManager.associateTag(idPhoto, idTag);
        themeManager.associateTag(idTheme, idTag);
        utilisateurManager.associatePhotoLike(id, idPhoto);
        */
        Utilisateur utilisateur = null;
        Utilisateur utilisateur2 = null;
        Utilisateur utilisateur3 = null;
        
        Theme theme = null;
        Theme theme2 = null;
        Theme theme3 = null;
        Theme theme4 = null;
        
        Long idUser = utilisateurManager.create("Nicole","Jonas","theplayer777", "jonas@jonas.ch", "1234");
        Long idUser2 = utilisateurManager.create("Norris", "Chuck", "WalkerTexasRanger","chuck@norris.king", "*****");
        Long idUser3 = utilisateurManager.create("Wayne", "Bruce", "Batman", "batman@gothamCity.com", "asdf");
        
        Long idTag = tagManager.create("beau");
        Long idTag2 = tagManager.create("taPhotoEstNul");
        Long idTag2Bis = tagManager.create("taPhotoEstNul");
        Long idTag3 = tagManager.create("chat");
        
        Long idTheme=0L;
        Long idTheme2=0L;
        //Long idTheme3=0L;
        Long idTheme4=0L;
        
        try{
            utilisateur = utilisateurManager.find(idUser);
            utilisateur2 = utilisateurManager.find(idUser2);
            utilisateur3 = utilisateurManager.find(idUser3);
            try {
                

                idTheme = themeManager.create("Neige",utilisateur.getId());
                idTheme2 = themeManager.create("Chien",utilisateur3.getId());
                idTheme4 = themeManager.create("Montagne",utilisateur.getId());
                
                theme = themeManager.find(idTheme);
                theme2 = themeManager.find(idTheme2);
                //theme3 = themeManager.find(idTheme3);
                theme4 = themeManager.find(idTheme4);
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch(Exception e){
        }
        
        Long idPhoto=0L;
        Long idPhoto2=0L;
        Long idPhoto3=0L;
        Long idPhoto4=0L;
        Long idPhoto5=0L;
        Long idPhoto6=0L;
        
        try{
        idPhoto = photoManager.create(0, "neige.jpg", utilisateur.getId(), theme);
        idPhoto2 = photoManager.create(3, "chien.jpg", utilisateur2.getId(), theme2);
        idPhoto3 = photoManager.create(0, "chien2.jpg", utilisateur3.getId(), theme2);
        idPhoto4 = photoManager.create(0, "montagne.jpg", utilisateur3.getId(), theme4);
        idPhoto5 = photoManager.create(0, "montagne2.jpg", utilisateur2.getId(), theme4);
        idPhoto6 = photoManager.create(0, "montagne3.jpg", utilisateur.getId(), theme4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        themeManager.associateTag(idTheme, idTag2);
        themeManager.associateTag(idTheme2, idTag3);
        themeManager.associateTag(idTheme, idTag3);
        themeManager.associateTag(idTheme4, idTag2);
        themeManager.associateTag(idTheme4, idTag);
        
    }

}
