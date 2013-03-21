package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Player;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julien Biedermann
 */
@Local
public interface PlayersManagerLocal {

    Long create(Long userId, int points);

    Player find(Long playerId);

    Player update(Long playerId, int points);
    
    void remove(Long playerId);

    void associateBadge(Long playerId, Long badgeId);

    List<Player> getLeaderboard();
}
