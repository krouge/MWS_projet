package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import javax.ejb.Stateless;

/**
 *
 * @author julien
 */
@Stateless
public class BadgesManager implements BadgesManagerLocal {

    @Override
    public Badge createBadge(String name, String descrition, String source) {
        return null;
    }

    

}
