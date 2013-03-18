package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.ApplicationsManagerLocal;
import ch.heigvd.comem.gameengine.services.BadgesManagerLocal;
import ch.heigvd.comem.gameengine.services.EventsManagerLocal;
import ch.heigvd.comem.gameengine.services.PlayersManagerLocal;
import ch.heigvd.comem.gameengine.services.RulesManagerLocal;
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
        applicationsManagerLocal.create("Kit-Kat",
                                        "Description", 
                                        "sd898sa98da8s9d8aa",
                                        "9d9Da23jhFkksls103D");
        
        applicationsManagerLocal.create("Cool pics",
                                        "Description", 
                                        "sadadasdsadsadasd8s9d8aa",
                                        "9dsdsdsadsagggdfdsc");
        
        // Création des joueurs
        
        playersManagerLocal.create("Julien",
                                   "Biedermann", 
                                   "julien.biedermann@gmail.com",
                                   0);
            
        playersManagerLocal.create("René",
                                   "Grossmann", 
                                   "rene.grossmann.skater@caramail.com",
                                   0);

        playersManagerLocal.create("Fabien",
                                   "Cornaz", 
                                   "fabien.licorne@caramail.com",
                                   0);
            
        playersManagerLocal.create("Jonas",
                                   "Nicole", 
                                   "jojolatorpie@caramail.com",
                                   0);
        
        // Création des badges
        
        for (int i=0; i<100; i++) {
            
            badgesManagerLocal.create("Badge "+i, 
                                      "Description badge " +i, 
                                      "http://rene.com/img.jpg");
            
        }
        
        //Règles
        
        Long app = applicationsManagerLocal.create("App", "Appdescr", "100", "300");

        for (int i=0; i<20; i++) {
            
            rulesManagerLocal.create("EventType Test "+1, 
                                      20, 
                                      applicationsManagerLocal.find(app));
        }
        
        // Evénements
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        long time = date.getTime();
        
        for (int i=0; i<100; i++) {
            
            eventsManagerLocal.create(applicationsManagerLocal.find(app), 
                    "Poster concours", 
                    new Timestamp(time));
        }
        
        
        
        
    }

    

}
