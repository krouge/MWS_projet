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
        Theme theme4 = null;
        
        Long idTheme1 = 0L;
        Long idTheme2 = 0L;
        Long idTheme3 = 0L;
        Long idTheme4 = 0L;
        
        try {
            idTheme1 = themeManager.create("Bachelorette Party",utilisateur1.getId());
            idTheme2 = themeManager.create("Cupcakes",utilisateur3.getId());
            idTheme3 = themeManager.create("Food",utilisateur1.getId());
            idTheme4 = themeManager.create("Comem",utilisateur1.getId());
            
            theme1 = themeManager.find(idTheme1);
            theme2 = themeManager.find(idTheme2);
            theme3 = themeManager.find(idTheme2);
            theme4 = themeManager.find(idTheme4);
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

        
        Long idPhoto3_1 = 0L;
        Long idPhoto3_2 = 0L;
        Long idPhoto3_3 = 0L;
        Long idPhoto3_4 = 0L;
        
        try{
            idPhoto3_1 = photoManager.create("Apserges #coquilleSaintJacques #Delicieux #Merci #ChezBidou",0, "food1.jpg", utilisateur2.getId(), idTheme3);
            idPhoto3_2 = photoManager.create("Entrée #cafetariaHEIG #yverdon #midi #HEIG-VD",0, "food2.jpg", utilisateur1.getId(), idTheme3);
            idPhoto3_3 = photoManager.create("Dessert #gâteau #Happybirthday #cadeau",0, "food3.jpg", utilisateur2.getId(), idTheme3);
            idPhoto3_4 = photoManager.create("Tarte fraise #fraise #creme #LausanneRestaurant",0, "food4.jpg", utilisateur3.getId(), idTheme3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        Long idTag3_1 = tagManager.create("#coquilleSaintJacques");
        Long idTag3_2 = tagManager.create("#Merci");
        Long idTag3_3 = tagManager.create("#ChezBidou");
        
        Long idTag3_4 = tagManager.create("#cafetariaHEIG");
        Long idTag3_5 = tagManager.create("#HEIG-VD");
        
        Long idTag3_6 = tagManager.create("#cadeau");
        Long idTag3_7 = tagManager.create("#ChezBidou");
        Long idTag3_8 = tagManager.create("#Happybirthday");
        
        Long idTag3_9 = tagManager.create("#fraise");
        Long idTag3_10 = tagManager.create("#creme");
        Long idTag3_11= tagManager.create("#LausanneRestaurant");
        
        photoManager.associateTag(idPhoto3_1, idTag3_1);
        photoManager.associateTag(idPhoto3_1, idTag3_2);
        //photoManager.associateTag(idPhoto3_1, idTag3_3);
        photoManager.associateTag(idPhoto3_1, idTag3_7);
        
        photoManager.associateTag(idPhoto3_2, idTag3_4);
        photoManager.associateTag(idPhoto3_2, idTag3_5);
        
        photoManager.associateTag(idPhoto3_3, idTag3_6);
        photoManager.associateTag(idPhoto3_3, idTag3_8);
        
        photoManager.associateTag(idPhoto3_4, idTag3_9);
        photoManager.associateTag(idPhoto3_4, idTag3_10);
        photoManager.associateTag(idPhoto3_4, idTag3_11);

        
        utilisateurManager.associatePhotoLike(idUser2, idPhoto3_2);
        utilisateurManager.associatePhotoLike(idUser1, idPhoto3_1);
        
        
        
        Long idPhoto4_1 = 0L;
        Long idPhoto4_2 = 0L;
        Long idPhoto4_3 = 0L;
        Long idPhoto4_4 = 0L;
        
        try{
            idPhoto4_1 = photoManager.create("Ambiance de classe #HEIG-VD #Bachelor #Comem #Ingenieur #Yverdon-les-Bains",0, "comem1.jpg", utilisateur1.getId(), idTheme4);
            idPhoto4_2 = photoManager.create("Le groupe de travail #Java #Groupe #Bidou #Rene #Fabien #Jonas",0, "comem2.jpg", utilisateur2.getId(), idTheme4);
            idPhoto4_3 = photoManager.create("L etage des chefs #St-rock #Comem",0, "comem3.jpg", utilisateur3.getId(), idTheme4);
            idPhoto4_4 = photoManager.create("T155 POWAAAA! #Porte #Salle #Classe #T255",0, "comem4.jpg", utilisateur3.getId(), idTheme4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        Long idTag4_1 = tagManager.create("#HEIG-VD");
        Long idTag4_2 = tagManager.create("#Bachelor");
        Long idTag4_3 = tagManager.create("#Comem");
        Long idTag4_4 = tagManager.create("#Ingenieur");
        Long idTag4_5 = tagManager.create("#Yverdon-les-Bains");
        Long idTag4_6 = tagManager.create("#Java");
        Long idTag4_7 = tagManager.create("#T155");
        
        
        photoManager.associateTag(idPhoto4_1, idTag4_1);
        photoManager.associateTag(idPhoto4_1, idTag4_2);
        photoManager.associateTag(idPhoto4_1, idTag4_3);
        photoManager.associateTag(idPhoto4_1, idTag4_4);
        photoManager.associateTag(idPhoto4_1, idTag4_5);
        
        photoManager.associateTag(idPhoto4_2, idTag4_6);
        
        photoManager.associateTag(idPhoto4_3, idTag4_3);
        
        photoManager.associateTag(idPhoto4_4, idTag4_7);
        
        utilisateurManager.associatePhotoLike(idUser3, idPhoto4_1);
        
        themeManager.associateTag(idTheme4, idTag4_1);
        themeManager.associateTag(idTheme4, idTag4_3);
                
    }

}
