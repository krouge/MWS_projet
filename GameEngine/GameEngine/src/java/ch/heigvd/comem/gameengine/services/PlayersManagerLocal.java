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
public interface PlayersManagerLocal {

    Long createPlayer(String firstName, String lastName, String email, int points);
    
}
