/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Event;
import ch.heigvd.comem.gameengine.model.Player;
import ch.heigvd.comem.gameengine.model.Rule;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Renouille
 */
@Stateless
public class GameManager implements GameManagerLocal {

    @EJB
    private PlayersManagerLocal playersManagerLocal;
    
    @EJB
    private EventsManagerLocal eventsManagerLocal;
    
    @EJB 
    private RulesManagerLocal rulesManagerLocal;
    
    @Override
    public void notifyEvent(long playerId, long eventId) {
        
        Player player = playersManagerLocal.find(playerId);
        Event event = eventsManagerLocal.find(eventId);
        
        String eventType = event.getEventType();
        
        Rule rule = rulesManagerLocal.findByEventType(eventType);
        
        player.setPoints(player.getPoints()+rule.getNumberOfPoints());
        
        if(rule.getBadge() != null) {
            playersManagerLocal.associateBadge(playerId, rule.getBadge().getBadgeId());
        }
        
        this.checkTotalOfPoints(player);
    }

    @Override
    public void checkTotalOfPoints(Player player) {
        
        int points = player.getPoints();
        Rule rule = null;
        if(points >= 5) {
            rule = rulesManagerLocal.findByEventType("points>5");
        } else if (points >= 10) {
            rule = rulesManagerLocal.findByEventType("points>10");
        }
        
        if(rule != null) {
            playersManagerLocal.associateBadge(player.getPlayerId(), rule.getBadge().getBadgeId());
        }
    }
    
    

    

}
