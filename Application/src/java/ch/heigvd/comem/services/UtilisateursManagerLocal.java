/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Utilisateur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jonas
 */
@Local
public interface UtilisateursManagerLocal {
    
    public Long create(String pseudo, String email, String mdp);
    
    public void delete(Long id) throws ExceptionIdUtilisateur;
    
    public Utilisateur find(Long id) throws ExceptionIdUtilisateur;
    
    public List<Utilisateur> findAll();
  
    public Utilisateur update(Long id, String pseudo, String email, String mdp) throws ExceptionIdUtilisateur;
    
    public void associatePhotoLike(Long id, Long idPhoto);

    public String login(String pseudoUser, String mdpUser);
    
}
