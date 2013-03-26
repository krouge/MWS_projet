/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import ch.heigvd.comem.config.GestionnaireGameEngine;
import ch.heigvd.comem.exceptions.ExceptionIdPhoto;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Photo;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


/**
 *
 * @author fabiencornaz
 */
@Stateless
@WebService
public class PhotosManager implements PhotosManagerLocal {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    UtilisateursManagerLocal utilisateursManager;
    
    @Override
    public Long create(int points, String source, Long utilisateurId,Theme theme) throws ExceptionIdUtilisateur{

        Utilisateur utilisateur = utilisateursManager.find(utilisateurId);
        Photo photo = new Photo();
        photo.setPoints(points);
        photo.setSource(source);
        photo.setUtilisateur(utilisateur);

        photo.setTheme(theme);

        em.persist(photo);
        em.flush();
        utilisateur.addPhoto(photo);

        theme.addPhoto(photo);
        
        String json = null;
        try {
            json = createEvent(utilisateur,GestionnaireGameEngine.API_KEY,GestionnaireGameEngine.API_SECRET,"post picture", new Date());
        } catch (JSONException ex) {
            Logger.getLogger(PhotosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return photo.getId();
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
    
    
    public Photo find(Long idPhoto) throws ExceptionIdPhoto{
        Photo photo = em.find(Photo.class, idPhoto);
        if (photo == null) {
            throw new ExceptionIdPhoto();
        }
        return photo;        
    }
    
    public Photo update (Long idPhoto,int points, String source, Utilisateur utilisateur,Theme theme)throws ExceptionIdPhoto{
        Photo photo = em.find(Photo.class, idPhoto);
        if (photo == null) {
            throw new ExceptionIdPhoto();
        }else{
            photo.setPoints(points);
            photo.setSource(source);
            photo.setUtilisateur(utilisateur);
            photo.setTheme(theme);
            
            return photo;
        }         
    }
    
    public void associateTag(Long idPhoto, Long idTag){
        
        Photo photo = em.find(Photo.class, idPhoto);
        Tag tag = em.find(Tag.class, idTag);
        
        tag.addPhoto(photo);
        photo.addTag(tag);
        
        em.flush();
    }
    
    @Override
    public void delete(Long idPhoto) throws ExceptionIdPhoto{
        Photo photo = em.find(Photo.class, idPhoto);
        
        if (photo == null) {
            throw new ExceptionIdPhoto();
        }
        em.remove(photo);
    }
    
    public List<Photo> findAll(){
        
        Query query = em.createQuery("SELECT p FROM Photo p");
        
        return (List<Photo>)query.getResultList();
        
    }


}
