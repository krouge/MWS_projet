package ch.heigvd.comem.test;

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

        Utilisateur utilisateur1 = null;
        Utilisateur utilisateur2 = null;
        Utilisateur utilisateur3 = null;
        
        Long idUser1 = utilisateurManager.create("Nicole","Jonas","theplayer777", "jonasnicole@yahoo.fr", "1234");
        Long idUser2 = utilisateurManager.create("Grossmann", "René", "renouille","rene@lucasart.com", "qwertz");
        Long idUser3 = utilisateurManager.create("Biedermann", "Julien", "bidou", "julien.biedermann@gmail.com", "asdf");
        
        try{
            utilisateur1 = utilisateurManager.find(idUser1);
            utilisateur2 = utilisateurManager.find(idUser2);
            utilisateur3 = utilisateurManager.find(idUser3);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Theme theme1 = null;
        Theme theme2 = null;
        Theme theme3 = null;
        
        Long idTheme1 = 0L;
        Long idTheme2 = 0L;
        Long idTheme3 = 0L;
        
        try {
            idTheme1 = themeManager.create("Bachelorette Party",utilisateur1.getId());
            idTheme2 = themeManager.create("Cupcakes",utilisateur3.getId());
            idTheme3 = themeManager.create("Montagne",utilisateur1.getId());
            
            theme1 = themeManager.find(idTheme1);
            theme2 = themeManager.find(idTheme2);
            theme3 = themeManager.find(idTheme2);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Long idPhoto1_1 = 0L;
        Long idPhoto1_2 = 0L;
        Long idPhoto1_3 = 0L;
        Long idPhoto1_4 = 0L;
        
        try{
            idPhoto1_1 = photoManager.create("Bachelorette Party #HEC #Bachelor #BF #Limo #Lausanne #Champagne #Party",0, "bachelorette-party.jpg", utilisateur1.getId(), idTheme1);
            idPhoto1_2 = photoManager.create("Karen's Party #Diploma #Graduation #Pub #Beer #Tequila #London",0, "party1.jpg", utilisateur2.getId(), idTheme1);
            idPhoto1_3 = photoManager.create("Disco Party #Queen #Longdrink #Troll #Disco #HappyHour",0, "party2.jpg", utilisateur3.getId(), idTheme1);
            idPhoto1_4 = photoManager.create("Soirée Pinky #Friends #Party #Night #Stockholm #Sweden #Dream",0, "party3.jpg", utilisateur3.getId(), idTheme1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        Long idTag1_1 = tagManager.create("#HEC");
        Long idTag1_2 = tagManager.create("#Bachelor");
        Long idTag1_3 = tagManager.create("#Queen");
        Long idTag1_4 = tagManager.create("#Stockholm");
        
        photoManager.associateTag(idPhoto1_1, idTag1_1);
        photoManager.associateTag(idPhoto1_1, idTag1_2);
        photoManager.associateTag(idPhoto1_3, idTag1_3);
        photoManager.associateTag(idPhoto1_4, idTag1_4);
        
        utilisateurManager.associatePhotoLike(idUser3, idPhoto1_1);
        
        themeManager.associateTag(idTheme1, idTag1_1);

        
    }

}
