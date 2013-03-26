/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.config.GestionnaireGameEngine;
import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Jonas
 */
@Stateless
public class ThemesManager implements ThemesManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private UtilisateursManagerLocal utilisateurManagerLocal;
    
    
    @Override
    public Long create(String titreTheme, Long utilisateurId) throws ExceptionIdUtilisateur{
        Theme theme = new Theme();
        
        Utilisateur utilisateur = new Utilisateur();
        utilisateur = utilisateurManagerLocal.find(utilisateurId);
        
        Query query = em.createQuery("SELECT t FROM Theme t WHERE t.titre LIKE :titre");
        query.setParameter("titre", titreTheme);
         
        if (query.getResultList().isEmpty()) {
             theme.setTitre(titreTheme);
             theme.setUtilisateur(utilisateur);
             
             em.persist(theme);
             em.flush();
             
             utilisateur.addTheme(theme);
             
            return theme.getId();
        }else{
            Theme themeExistant = (Theme) query.getSingleResult();
       
            return themeExistant.getId();
        }
        
    }
    
    private String createEvent(Utilisateur utilisateur, String API_KEY, String API_SECRET, String creationPhoto, Date date) throws JSONException {
        ClientConfig cc = new DefaultClientConfig();
        Client c = Client.create(cc);

        WebResource r = c.resource("http://localhost:8081/GameEngine/resources/events");
        
        JSONObject jsonPrincipal = new JSONObject();
        
        JSONObject player = new JSONObject();
        player.put("playerId", utilisateur.getIdPlayer());
        
        JSONObject app = new JSONObject();
        app.put("apiKey", API_KEY);
        app.put("apiSecret", API_SECRET);
        
        jsonPrincipal.put("player", player);
        jsonPrincipal.put("application", app);
        jsonPrincipal.put("eventType", creationPhoto);        
        
        ClientResponse response = r.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonPrincipal);

        return jsonPrincipal.toString();
    }

    public void delete(Long id) throws ExceptionIdTheme {
        Theme theme = em.find(Theme.class, id);
        
        if(theme != null){
            em.remove(theme);
        }else{
            throw new ExceptionIdTheme();
        }
    }
    
    public Theme find(Long id) throws ExceptionIdTheme{
        Theme theme = em.find(Theme.class, id);
        
        if(theme == null){
            throw new ExceptionIdTheme();
        }
        return theme;
    }
    
    public Theme update(Long id, String titre) throws ExceptionIdTheme{
        Theme theme = em.find(Theme.class, id);
        theme.setTitre(titre);
        return theme;
    }
    
    public void associateTag(Long idTheme, Long idTag){
        Theme theme = em.find(Theme.class, idTheme);
        Tag tag = em.find(Tag.class, idTag);
        theme.addTag(tag);
        tag.addTheme(theme);
        em.flush();
    }
    
    public List<Theme> findAll(){
        
        Query query = em.createQuery("SELECT t FROM Theme t");
        
        return (List<Theme>)query.getResultList();
    }



}
