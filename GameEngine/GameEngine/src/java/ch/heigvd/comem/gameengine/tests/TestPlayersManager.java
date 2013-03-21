package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.PlayersManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Julien Biedermann
 */
@Stateless
public class TestPlayersManager implements TestPlayersManagerLocal {

    @EJB
    private PlayersManagerLocal playersManagerLocal;
    
    @Override
    public void generatePlayers() {
        
            
            playersManagerLocal.create(0);
            
            playersManagerLocal.create(0);

            playersManagerLocal.create(0);
            
            playersManagerLocal.create(0);
        
    }

    

}
