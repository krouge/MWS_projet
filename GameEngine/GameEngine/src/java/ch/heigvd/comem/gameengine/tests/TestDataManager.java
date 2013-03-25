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
        Long julien = playersManagerLocal.create(50);
            
        Long rene = playersManagerLocal.create(30);

        Long fabien = playersManagerLocal.create(10);
            
        Long jonas = playersManagerLocal.create(0);
        
        // Création des badges
        
        for (int i=0; i<10; i++) {
            
            Long badge = badgesManagerLocal.create("Badge "+i, 
                                      "Description badge " +i, 
                                      "http://rene.com/img.jpg");
            
            playersManagerLocal.associateBadge(rene, badge);
            
            Long badge2 = badgesManagerLocal.create("Badge "+i, 
                                      "Description badge " +i, 
                                      "http://julien.com/img.jpg");
            
            playersManagerLocal.associateBadge(julien, badge2);
            
        }
        
        //Règles
        
        Long badgeRule = badgesManagerLocal.create("Badge ", 
                                      "Description badge ", 
                                      "http://rene.com/img.jpg");
            
        playersManagerLocal.associateBadge(rene, badgeRule);
        
        for (int i=0; i<10; i++) {
            
            Long rule = rulesManagerLocal.create("CreationPhoto", 
                                      20, 
                                      apiKey, apiSecret);
            
            rulesManagerLocal.associateBadge(rule, badgeRule);
        }
        
        // Evénements
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        long time = date.getTime();
        /*
         //Long event = eventsManagerLocal.create(playersManagerLocal.find(julien), 
                    "1234", "1234", 
                    "Poster concours", 
                    new Timestamp(time));
            

        Long event2 = eventsManagerLocal.create(playersManagerLocal.find(rene), 
                "1234", "1234", 
                "Poster concours", 
                new Timestamp(time));


        Long event3 = eventsManagerLocal.create(playersManagerLocal.find(fabien), 
                "1234", "1234", 
                "Poster concours", 
                new Timestamp(time));

        Long event4 = eventsManagerLocal.create(playersManagerLocal.find(jonas),
                "1234", "1234", 
                "Poster concours", 
                new Timestamp(time));

*/
    }
    
}
