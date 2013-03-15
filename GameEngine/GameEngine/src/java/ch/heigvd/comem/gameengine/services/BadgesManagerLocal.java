/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import ch.heigvd.comem.gameengine.model.Badge;
import javax.ejb.Local;

/**
 *
 * @author julien
 */
@Local
public interface BadgesManagerLocal {

    Long create(String name, String description, String source);

    void remove(Long id);

    Badge find(Long badgeId);

    Badge update(Long badgeId, String nom, String description, String source);
    
}
