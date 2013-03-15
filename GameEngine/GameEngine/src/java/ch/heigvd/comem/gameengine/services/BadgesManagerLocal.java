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

    Badge createBadge(String name, String descrition, String source);
    
}
