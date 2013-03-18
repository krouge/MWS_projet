package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.BadgesManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author Julien Biedermann
 */
@Stateless
@WebService
public class TestBadgesManager implements TestBadgesManagerLocal {
    
    @EJB
    private BadgesManagerLocal badgesManagerLocal;

    @Override
    public void generateBadges() {
        
        for (int i=0; i<100; i++) {
            
            badgesManagerLocal.create("Badge "+i, 
                                      "Description badge " +i, 
                                      "http://rene.com/img.jpg");
            
        }
        
    }
    
    
    


}
