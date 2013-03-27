package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.ApplicationsManagerLocal;
import ch.heigvd.comem.gameengine.services.BadgesManagerLocal;
import ch.heigvd.comem.gameengine.services.EventsManagerLocal;
import ch.heigvd.comem.gameengine.services.PlayersManagerLocal;
import ch.heigvd.comem.gameengine.services.RulesManagerLocal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author Julien Biedermann
 */
@Stateless
@WebService
public class TestDataManager implements TestDataManagerLocal {
    
    @EJB
    ApplicationsManagerLocal applicationsManagerLocal;
    @EJB
    BadgesManagerLocal badgesManagerLocal;
    @EJB
    EventsManagerLocal eventsManagerLocal;
    @EJB
    PlayersManagerLocal playersManagerLocal;
    @EJB
    RulesManagerLocal rulesManagerLocal;

    @Override
    public void generateData() {
        
        // Création des applications
        
        String apiKey ="1234";
        String apiSecret = "1234";
        
        
        Long app1 = applicationsManagerLocal.create("Reflex",
                                        "Description", 
                                        "1234",
                                        "1234");
        
        Long app2 = applicationsManagerLocal.create("Cool pics",
                                        "Description", 
                                        "sadadasdsadsadasd8s9d8aa",
                                        "9dsdsdsadsagggdfdsc");
        
       
        // Création des joueurs
//        Long julien = playersManagerLocal.create(0);
//            
//        Long rene = playersManagerLocal.create(0);
//
//        Long fabien = playersManagerLocal.create(0);
//            
//        Long jonas = playersManagerLocal.create(0);
        
        // Création des badges
        
        Long badge1 = badgesManagerLocal.create("Badge 5p", 
                                      "Player's points > 5", 
                                      "http://rene.com/img.jpg");
            
            
        Long badge2 = badgesManagerLocal.create("Badge 10p", 
                                      "Player's points > 10", 
                                      "http://julien.com/img.jpg");
//        playersManagerLocal.associateBadge(fabien, badge2);
            
        //Création des badges
        
        Long rule5p = rulesManagerLocal.create("points>5", 0, apiKey, apiSecret, badge1);
        
        Long rule10p = rulesManagerLocal.create("points>10", 0, apiKey, apiSecret, badge2);
        
        Long rulePostPicture = rulesManagerLocal.create("post picture", 5, apiKey, apiSecret);
        
        Long ruleLikePicture = rulesManagerLocal.create("like picture", 1, apiKey, apiSecret);
        
        Long rulePostTheme = rulesManagerLocal.create("post theme", 5, apiKey, apiSecret);
        
        // Evénements
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Long time = date.getTime();

        
        //Long event = eventsManagerLocal.create(1L, "1234", "1234", "post theme", new Timestamp(time));
        //Long event2 = eventsManagerLocal.create(1L, "1234", "1234", "post theme", new Timestamp(time));

    }
    
}
