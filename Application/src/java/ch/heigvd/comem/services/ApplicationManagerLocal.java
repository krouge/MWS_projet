/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.model.Photo;
import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface ApplicationManagerLocal {

    String uploadFile(Photo entity);
    
}
