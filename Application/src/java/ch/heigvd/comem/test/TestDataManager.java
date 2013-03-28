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
        Utilisateur utilisateur2 = null;
        Utilisateur utilisateur3 = null;
        
        Theme theme = null;
        Theme theme2 = null;
        Theme theme3 = null;
        Theme theme4 = null;
        
        Long idUser = utilisateurManager.create("Nicole","Jonas","theplayer777", "jonas@jonas.ch", "1234");
        Long idUser2 = utilisateurManager.create("Norris", "Chuck", "WalkerTexasRanger","chuck@norris.king", "*****");
        Long idUser3 = utilisateurManager.create("Wayne", "Bruce", "Batman", "batman@gothamCity.com", "asdf");
        
        Long idTag = tagManager.create("neige");
        Long idTag2 = tagManager.create("paysage");
        Long idTag3 = tagManager.create("chien");
        Long idTag4 = tagManager.create("couleur");
        Long idTag5 = tagManager.create("montagne");
        Long idTag6 = tagManager.create("sapin");
        Long idTag7 = tagManager.create("route");
        Long idTag8 = tagManager.create("animaux");

        
        Long idTheme=0L;
        Long idTheme2=0L;
        Long idTheme3=0L;
        
        try{
            utilisateur = utilisateurManager.find(idUser);
            utilisateur2 = utilisateurManager.find(idUser2);
            utilisateur3 = utilisateurManager.find(idUser3);
            try {
                

                idTheme = themeManager.create("Neige",utilisateur.getId());
                idTheme2 = themeManager.create("Chien",utilisateur3.getId());
                idTheme3 = themeManager.create("Montagne",utilisateur.getId());
                
                theme = themeManager.find(idTheme);
                theme2 = themeManager.find(idTheme2);
                theme3 = themeManager.find(idTheme3);
                
                
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
        Long idPhoto7=0L;
        
        try{
        idPhoto = photoManager.create(0, "neige.jpg", utilisateur.getId(), idTheme);
        idPhoto2 = photoManager.create(3, "chien.jpg", utilisateur2.getId(), idTheme2);
        idPhoto3 = photoManager.create(0, "chien2.jpg", utilisateur3.getId(), idTheme2);
        idPhoto7 = photoManager.create(0, "chien3.jpg", utilisateur3.getId(), idTheme2);
        idPhoto4 = photoManager.create(0, "montagne.jpg", utilisateur3.getId(), idTheme3);
        idPhoto5 = photoManager.create(0, "montagne2.jpg", utilisateur2.getId(), idTheme3);
        idPhoto6 = photoManager.create(0, "montagne3.jpg", utilisateur.getId(), idTheme3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        photoManager.associateTag(idPhoto, idTag);
        photoManager.associateTag(idPhoto, idTag2);
        photoManager.associateTag(idPhoto, idTag6);
        photoManager.associateTag(idPhoto, idTag7);
        
        photoManager.associateTag(idPhoto2, idTag3);
        photoManager.associateTag(idPhoto2, idTag4);
        photoManager.associateTag(idPhoto2, idTag8);
        
        photoManager.associateTag(idPhoto3, idTag3);
        photoManager.associateTag(idPhoto3, idTag4);
        photoManager.associateTag(idPhoto3, idTag8);
        photoManager.associateTag(idPhoto3, idTag);
        
        photoManager.associateTag(idPhoto7, idTag3);
        photoManager.associateTag(idPhoto7, idTag4);
        photoManager.associateTag(idPhoto7, idTag8);
        
        
        photoManager.associateTag(idPhoto4, idTag);
        photoManager.associateTag(idPhoto4, idTag2);
        photoManager.associateTag(idPhoto4, idTag4);
        photoManager.associateTag(idPhoto4, idTag5);
        photoManager.associateTag(idPhoto4, idTag6);
        
        photoManager.associateTag(idPhoto5, idTag);
        photoManager.associateTag(idPhoto5, idTag2);
        photoManager.associateTag(idPhoto5, idTag5);
        
        photoManager.associateTag(idPhoto6, idTag);
        photoManager.associateTag(idPhoto6, idTag2);
        photoManager.associateTag(idPhoto6, idTag4);
        photoManager.associateTag(idPhoto6, idTag5);
        photoManager.associateTag(idPhoto6, idTag6);
        
        
        utilisateurManager.associatePhotoLike(idUser3, idPhoto);
        utilisateurManager.associatePhotoLike(idUser2, idPhoto4);
        utilisateurManager.associatePhotoLike(idUser, idPhoto4);
        utilisateurManager.associatePhotoLike(idUser3, idPhoto4);
        utilisateurManager.associatePhotoLike(idUser, idPhoto5);
        utilisateurManager.associatePhotoLike(idUser3, idPhoto6);
        
        themeManager.associateTag(idTheme, idTag);
        
        
        themeManager.associateTag(idTheme2, idTag3);
        themeManager.associateTag(idTheme2, idTag8);
        
        
        themeManager.associateTag(idTheme3, idTag);
        themeManager.associateTag(idTheme3, idTag2);
        themeManager.associateTag(idTheme3, idTag5);
        themeManager.associateTag(idTheme3, idTag6);
        
    }

}
