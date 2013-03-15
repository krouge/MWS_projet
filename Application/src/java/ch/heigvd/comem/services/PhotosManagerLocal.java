/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.model.Utilisateur;
import javax.ejb.Local;

/**
 *
 * @author fabiencornaz
 */
@Local
public interface PhotosManagerLocal {
    
    public Long createPhoto(int points, String source, Utilisateur ustilisateur);
    public void remove(Object object);
}
