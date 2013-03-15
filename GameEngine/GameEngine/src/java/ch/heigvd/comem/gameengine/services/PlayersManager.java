package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Player;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author julien
 */
@Stateless
public class PlayersManager implements PlayersManagerLocal {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Long createPlayer(String firstName, String lastName, String email, int points) {
        
        Player player = new Player();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setEmail(email);
        player.setPoints(points);
        em.persist(player); em.flush();
        
        return player.getPlayerId();
        
    }

    

}
