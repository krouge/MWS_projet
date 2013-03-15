/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.model.Utilisateur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jonas
 */
@Stateless
public class UtilisateursManager implements UtilisateursManagerLocal {

    @PersistenceContext
    private EntityManager em;

    
    public Long createUtilisateur(String pseudo, String email, String mdp){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(pseudo);
        utilisateur.setEmail(email);
        utilisateur.setMdp(mdp);
        em.persist(utilisateur);
        em.flush();
        return utilisateur.getId();
        
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
