package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import ch.heigvd.comem.gameengine.model.Player;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author julien
 */
@Stateless
@WebService
public class PlayersManager implements PlayersManagerLocal {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Long create(int points) {
        
        Player player = new Player();
        player.setPoints(points);
        em.persist(player); em.flush();
        
        return player.getPlayerId();
        
    }
    
    @Override
    public Player find(Long playerId) {
        
        Player player = em.find(Player.class, playerId);
        
        return player;
        
    }
    
    @Override
    public Player update(Long playerId, int points) {
        
        Player player = em.find(Player.class, playerId);
        player.setPoints(points);
        
        return player;
        
    }

    @Override
    public void remove(Long playerId) {
        
        Player player = em.find(Player.class, playerId);
        em.remove(player);
        
    }

    @Override
    public void associateBadge(Long playerId, Long badgeId) {
        
        Player player = em.find(Player.class, playerId);
        Badge badge = em.find(Badge.class, badgeId);
        
        player.addBadge(badge);
        badge.addPlayer(player);
    }

    @Override
    public List<Player> getLeaderboard() {
        
        Query query = em.createQuery("SELECT p FROM Player AS p ORDER BY p.points DESC");

        List<Player> leaderboard = (List<Player>)query.getResultList();
        
        return leaderboard;
    }
    
    
}
