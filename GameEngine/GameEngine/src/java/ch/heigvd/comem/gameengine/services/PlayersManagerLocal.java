package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Player;
import javax.ejb.Local;

/**
 *
 * @author Julien Biedermann
 */
@Local
public interface PlayersManagerLocal {

    Long create(String firstName, String lastName, String email, int points);

    Player find(Long playerId);

    Player update(Long playerId, String firstName, String lastName, String email, int points);
    
    void remove(Long playerId);

    void associateBadge(Long playerId, Long badgeId);
}
