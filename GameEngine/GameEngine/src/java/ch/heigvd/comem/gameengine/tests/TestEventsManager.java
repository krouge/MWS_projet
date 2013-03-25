/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.ApplicationsManagerLocal;
import ch.heigvd.comem.gameengine.services.EventsManagerLocal;
import ch.heigvd.comem.gameengine.services.PlayersManagerLocal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Renouille
 */
@Stateless
public class TestEventsManager implements TestEventsManagerLocal {

        @EJB
        private EventsManagerLocal eventsManagerLocal;
        @EJB
        private PlayersManagerLocal playersManagerLocal;
        @EJB
        private ApplicationsManagerLocal applicationsManagerLocal;

    @Override
    public void generateEvents() {

        Long player = playersManagerLocal.create(0);
        Long app = applicationsManagerLocal.create("App event", "Event app", "200", "300");
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        long time = date.getTime();
        
        for (int i=0; i<100; i++) {
            
            eventsManagerLocal.create(playersManagerLocal.find(player),
                    "sd898sa98da8s9d8aa", "9d9Da23jhFkksls103D", 
                    "Poster concours", 
                    new Timestamp(time));
        }
    }
        
        

}
