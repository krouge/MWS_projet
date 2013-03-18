package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.PlayersManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author Julien Biedermann
 */
@Stateless
@WebService
public class TestPlayersManager implements TestPlayersManagerLocal {

    @EJB
    private PlayersManagerLocal playersManagerLocal;
    
    @Override
    public void generatePlayers() {
        
            
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
        
    }

    

}
