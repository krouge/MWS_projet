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
        
            
            playersManagerLocal.create(1L, 0);
            
            playersManagerLocal.create(2L, 0);

            playersManagerLocal.create(3L, 0);
            
            playersManagerLocal.create(4L, 0);
        
    }

    

}
