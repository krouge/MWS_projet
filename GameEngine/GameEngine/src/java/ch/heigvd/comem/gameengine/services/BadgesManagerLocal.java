/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.services;

import javax.ejb.Local;

/**
 *
 * @author julien
 */
@Local
public interface BadgesManagerLocal {

    Long createBadge(String name, String descrition, String source);
    
}
