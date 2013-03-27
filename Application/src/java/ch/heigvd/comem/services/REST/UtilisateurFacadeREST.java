/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.dto.PhotoDTO;
import ch.heigvd.comem.dto.ThemeDTO;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Utilisateur;
import ch.heigvd.comem.dto.UtilisateurDTO;
import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.services.UtilisateursManagerLocal;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Jonas
 */
@Stateless
@Path("utilisateurs")
public class UtilisateurFacadeREST{
    
    @EJB
    private UtilisateursManagerLocal utilisateurManager; 

    public UtilisateurFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Utilisateur entity) {
        utilisateurManager.create(entity.getNom(), entity.getPrenom(), entity.getPseudo(), entity.getEmail(), entity.getMdp());
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Utilisateur entity) throws ExceptionIdUtilisateur {
            utilisateurManager.update(entity.getId(),entity.getPseudo(), entity.getEmail(), entity.getMdp());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdUtilisateur { 
        utilisateurManager.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public UtilisateurDTO find(@PathParam("id") Long id, @QueryParam("themes") Long withThemes, @QueryParam("photos") Long withPhotos, @QueryParam("like") Long withLike) throws ExceptionIdUtilisateur {

        Utilisateur utilisateur = utilisateurManager.find(id);
        
        
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        
        utilisateurDTO.setId(utilisateur.getId());
        utilisateurDTO.setEmail(utilisateur.getEmail());
        utilisateurDTO.setMdp(utilisateur.getMdp());
        utilisateurDTO.setPseudo(utilisateur.getPseudo());
        utilisateurDTO.setIdPlayer(utilisateur.getIdPlayer());
        
       
        if(withThemes != null && withThemes == 1){
            List<ThemeDTO> themeDTOS = new LinkedList<ThemeDTO>();
            List<Theme> themes = utilisateur.getThemes();

            for(Theme theme : themes){
                    
                ThemeDTO themeDTO = new ThemeDTO();
                themeDTO.setId(theme.getId());
                themeDTO.setTitre(theme.getTitre());

                themeDTOS.add(themeDTO);

            }

            utilisateurDTO.setThemes(themeDTOS);
        }
        
        if(withPhotos != null && withPhotos == 1){
            
            List<PhotoDTO> photoDTOS = new LinkedList<PhotoDTO>();
            List<Photo> photos = utilisateur.getPhotos();
            
            for(Photo photo : photos){
                
                PhotoDTO photoDto = new PhotoDTO();
                photoDto.setId(photo.getId());
                photoDto.setPoints(photo.getPoints());
                photoDto.setSource(photo.getSource());
                
                photoDTOS.add(photoDto);
                
            }
            
            utilisateurDTO.setPhotos(photoDTOS);
            
        }
        
        /*
        if(withLike == null && withLike == 1){
            utilisateurDTO.setPhotos_like(utilisateur.getPhotos_like());
        }
        */
        
        return utilisateurDTO;
    }
    
    
    @POST
    @Path("login")
    @Consumes({"application/xml", "application/json"})
    @Produces("application/json")
    public Utilisateur login(Utilisateur entity) {

        return utilisateurManager.login(entity.getPseudo(),entity.getMdp());
    }
    
    
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List<UtilisateurDTO> findAll(@QueryParam("themes") Long withThemes, @QueryParam("photos") Long withPhotos, @QueryParam("like") Long withLike) {
        
        List<Utilisateur> utilisateurs = utilisateurManager.findAll();
        List<UtilisateurDTO> utilisateursDTO = new LinkedList<UtilisateurDTO>();
        
        for(Utilisateur utilisateur : utilisateurs){
            UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
            utilisateurDTO.setId(utilisateur.getId());
            utilisateurDTO.setEmail(utilisateur.getEmail());
            utilisateurDTO.setMdp(utilisateur.getMdp());
            utilisateurDTO.setPseudo(utilisateur.getPseudo());
            utilisateurDTO.setIdPlayer(utilisateur.getIdPlayer());
            
            if(withThemes != null && withThemes == 1){
                
                List<ThemeDTO> themeDTOS = new LinkedList<ThemeDTO>();
                List<Theme> themes = utilisateur.getThemes();

                for(Theme theme : themes){

                    ThemeDTO themeDTO = new ThemeDTO();
                    themeDTO.setId(theme.getId());
                    themeDTO.setTitre(theme.getTitre());

                    themeDTOS.add(themeDTO);

                }

                utilisateurDTO.setThemes(themeDTOS);
            }
        
            if(withPhotos != null && withPhotos == 1){
                
                List<PhotoDTO> photoDTOS = new LinkedList<PhotoDTO>();
                List<Photo> photos = utilisateur.getPhotos();

                for(Photo photo : photos){

                    PhotoDTO photoDto = new PhotoDTO();
                    photoDto.setId(photo.getId());
                    photoDto.setPoints(photo.getPoints());
                    photoDto.setSource(photo.getSource());

                    photoDTOS.add(photoDto);

                }

                utilisateurDTO.setPhotos(photoDTOS);
                }
            
            /*
            if(withLike == null && withLike == 1){
                utilisateurDTO.setPhotos_like(utilisateur.getPhotos_like());
            }s
            
            */
            utilisateursDTO.add(utilisateurDTO);
        }
       
        return utilisateursDTO;
    }

    /**
     * 
     * @return
     * @throws JSONException 
     */
    @GET
    @Path("leaderboard")
    @Consumes({"application/json"})
    @Produces("application/json")
    public String leaderBoard() throws JSONException {
        
        ClientConfig cc = new DefaultClientConfig();
        Client c = Client.create(cc);
        WebResource r = c.resource("http://localhost:8081/GameEngine/resources/players/leaderboard");
        ClientResponse response = r.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        
        JSONObject json = new JSONObject(response.getEntity(String.class));
        JSONArray playerArray = json.getJSONArray("player");
        JSONArray jsonPrincipal = new JSONArray();
        
        for (int i = 0; i < playerArray.length(); i++) {
            
            JSONObject player = playerArray.getJSONObject(i);
            
            Long idPlayer =Long.parseLong(player.getString("playerId"));
            int points = Integer.parseInt(player.getString("points"));
            
            Utilisateur utilisateur = utilisateurManager.findByIdPlayer(idPlayer);
            
            JSONObject jsonPlayer = new JSONObject();
            jsonPlayer.put("points", points);
            jsonPlayer.put("pseudo", utilisateur.getPseudo());
            
            jsonPrincipal.put(jsonPlayer);
            
            
        }
       
        return jsonPrincipal.toString();
       
    }
    
    
    @GET
    @Path("{id}/profil")
    @Consumes({"application/json"})
    @Produces("application/json")
    public String profilUtilisateur(@PathParam("id") Long id) throws JSONException, ExceptionIdUtilisateur {
        
        ClientConfig cc = new DefaultClientConfig();
        Client c = Client.create(cc);
        WebResource r = c.resource("http://localhost:8081/GameEngine/resources/players/"+id);
        //Utilisateur request = r.accept(MediaType.APPLICATION_JSON_TYPE,MediaType.APPLICATION_XML_TYPE).type(MediaType.APPLICATION_JSON_TYPE).post(Utilisateur.class, jsonObject);        
        ClientResponse response = r.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
        
        
        JSONObject json = new JSONObject(response.getEntity(String.class));
        
        JSONObject jsonPrincipal = new JSONObject();
        
        //player
        JSONObject badgesArray = json.getJSONObject("badges");
        
        jsonPrincipal.put("badges", badgesArray);
        
        
        //utilisateurs
        Utilisateur utilisateur = utilisateurManager.find(id);
        
        JSONArray photoArray = new JSONArray();
        
        for(Photo photo : utilisateur.getPhotos()){
            
            JSONObject photoJSON = new JSONObject();
            photoJSON.put("source", photo.getSource());
            
            photoArray.put(photoJSON);
        }
        
        jsonPrincipal.put("photos", photoArray);
        
        //infos
        jsonPrincipal.put("pseudo", utilisateur.getPseudo());
        jsonPrincipal.put("email", utilisateur.getEmail());

        
        /*
        JSONArray playerArray = json.getJSONArray("player");
        //json = json.getJSONArray("player");
        JSONArray jsonPrincipal = new JSONArray();
        
        for (int i = 0; i < playerArray.length(); i++) {
            
            JSONObject player = playerArray.getJSONObject(i);
            
            Long idPlayer =Long.parseLong(player.getString("playerId"));
            int points = Integer.parseInt(player.getString("points"));
            
            Utilisateur utilisateur = utilisateurManager.findByIdPlayer(idPlayer);
            
            JSONObject jsonPlayer = new JSONObject();
            jsonPlayer.put("points", points);
            jsonPlayer.put("pseudo", utilisateur.getPseudo());
            
            jsonPrincipal.put(jsonPlayer);
            
            
        }
       */
        return jsonPrincipal.toString();
       
    }
    
    
}
