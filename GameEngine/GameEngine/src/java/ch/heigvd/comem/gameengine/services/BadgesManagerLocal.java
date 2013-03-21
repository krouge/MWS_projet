package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julien Biedermann
 */
@Local
public interface BadgesManagerLocal {

    Long create(String name, String description, String source);

    Badge find(Long badgeId);
    
    List<Badge> findAll();

    Badge update(Long badgeId, String name, String description, String source);
    
    void delete(Long badgeId);
}
