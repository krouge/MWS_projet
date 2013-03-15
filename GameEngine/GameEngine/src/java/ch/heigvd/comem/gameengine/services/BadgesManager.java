package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author julien
 */
@Stateless
@WebService
public class BadgesManager implements BadgesManagerLocal {

    @Override
    public Badge createBadge(String name, String descrition, String source) {
        
        Badge badge = new Badge();
        badge.setName(name);
        badge.setDescription(descrition);
        
        return badge;
    }

    

}
