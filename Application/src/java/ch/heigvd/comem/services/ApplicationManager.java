/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.model.Photo;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Renouille
 */
@Stateless
public class ApplicationManager implements ApplicationManagerLocal {
    
    @EJB
    private PhotosManagerLocal photosManager;
    
    @Override
    public String uploadFile(Photo entity) {
        
        
        return null;
    }
}
