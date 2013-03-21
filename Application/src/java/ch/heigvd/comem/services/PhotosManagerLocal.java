/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdPhoto;
import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author fabiencornaz
 */
@Local
public interface PhotosManagerLocal {
    
    public Long create(int points, String source, Utilisateur ustilisateur, Theme theme);
    public void delete(Long idPhoto) throws ExceptionIdPhoto;
    public Photo find(Long idPhoto) throws ExceptionIdPhoto;  
    public void associateTag(Long idPhoto, Long idTag);
    public Photo update (Long idPhoto,int points, String source, Utilisateur utilisateur,Theme theme )throws ExceptionIdPhoto;
    public List<Photo> findAll();
    
}
