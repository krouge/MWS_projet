
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.config.GestionnaireGameEngine;
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
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Service REST permettant le actions POST / PUT / DELETE /GET sur un utilisateur
 * @author Jonas
 */
@Stateless
@Path("utilisateurs")
public class UtilisateurFacadeREST{
    
    @EJB
    private UtilisateursManagerLocal utilisateurManager; 

    public UtilisateurFacadeREST() {
    }
    
    /**
     * Permet de créer une nouvelle entité utilisateur
     * @param entity l' entité utilisateur a créer
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(Utilisateur entity) {
        
        Long idUtilisateur = utilisateurManager.create(entity.getNom(), entity.getPrenom(), entity.getPseudo(), entity.getEmail(), entity.getMdp());
        
        if(idUtilisateur == null) {
            return Response.status(HttpServletResponse.SC_FORBIDDEN).build();
        } else {
            return Response.status(HttpServletResponse.SC_OK).build();
        }
    }
    
    /**
     * Permet de modifier une entité utilisateur
     * @param id id de l'entité utilisateur a modifié
     * @param entity l'entité utilisateur a modifié
     * @throws ExceptionIdUtilisateur si l'id de l'utilisateur n'existe pas
     */
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Utilisateur entity) throws ExceptionIdUtilisateur {
            utilisateurManager.update(entity.getId(),entity.getPseudo(), entity.getEmail(), entity.getMdp());
    }
    
    /**
     * Permet de supprimer une entité utilisateur
     * @param id l'id de l'entité utilisateur a supprimé
     * @throws ExceptionIdUtilisateur si l'id de l'utilisateur n'existe pas
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws ExceptionIdUtilisateur { 
        utilisateurManager.delete(id);
    }
    
    /**
     * Permet de récupérer l'entité utilisateur correspondant à l'id passé en paramètre avec ses thèmes et ou ses photos.
     * @param id id de l'entité utilisateur a récupérer
     * @param withThemes si withThemes = 1 alors retourne les thèmes liés à l'entité utilisateur
     * @param withPhotos si withPhotos = 1 alors retourne les thèmes liés à l'entité utilisateur
     * @return UtilisateurDTO une entité utilisateur personalisée (voir classe UtilisateurDTO)
     * @throws ExceptionIdUtilisateur si l'id de l'utilisateur n'existe pas 
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public UtilisateurDTO find(@PathParam("id") Long id, @QueryParam("themes") Long withThemes, @QueryParam("photos") Long withPhotos) throws ExceptionIdUtilisateur {

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
                photoDto.setTitre(photo.getTitre());
                photoDto.setId(photo.getId());
                photoDto.setPoints(photo.getPoints());
                photoDto.setSource(photo.getSource());
                
                photoDTOS.add(photoDto);
                
            }
            
            utilisateurDTO.setPhotos(photoDTOS);
            
        }
        
        return utilisateurDTO;
    }
    
    /**
     * Permet de logguer un utilisateur dans l'application
     * @param entity une entité utilisateur avec un mot de passe et un pseudo
     * @return Response si l'utilisateur est null retourne une Erreur 403 sinon retourne l'entité utilisateur
     */
    @POST
    @Path("login")
    @Consumes({"application/xml", "application/json"})
    @Produces("application/json")
    public Response login(Utilisateur entity) {

        Utilisateur utilisateur  = utilisateurManager.login(entity.getPseudo(),entity.getMdp());
        
        if(utilisateur == null) {
            return Response.status(HttpServletResponse.SC_FORBIDDEN).build();
        } else {
            
            return Response.status(HttpServletResponse.SC_OK).entity(utilisateur.getId().toString()).build();
        }
    }
    
    /**
     * Permet de récupérer toutes les entités utilisateurs avec leurs thèmes et/ou leurs photos
     * @param withThemes si withThemes = 1 alors retourne les thèmes liés à une entité utilisateur
     * @param withPhotos si withPhotos = 1 alors retourne les thèmes liés à une entité utilisateur
     * @return List<UtilisateurDTO> la liste de toutes les entités utilisateur personalisée en UtilisateurDTO (voir classe UtilisateurDTO)
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<UtilisateurDTO> findAll(@QueryParam("themes") Long withThemes, @QueryParam("photos") Long withPhotos) {
        
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
                    photoDto.setTitre(photo.getTitre());
                    photoDto.setPoints(photo.getPoints());
                    photoDto.setSource(photo.getSource());

                    photoDTOS.add(photoDto);

                }

                utilisateurDTO.setPhotos(photoDTOS);
                }

            utilisateursDTO.add(utilisateurDTO);
        }
       
        return utilisateursDTO;
    }

    /**
     * Permet de récupérer le classement de l'application avec ses players 
     * @return String un json comprenant le classement
     * @throws JSONException 
     */
    @GET
    @Path("leaderboard")
    @Consumes({"application/json"})
    @Produces("application/json")
    public String leaderBoard() throws JSONException {
        
        ClientConfig cc = new DefaultClientConfig();
        Client c = Client.create(cc);
        WebResource r = c.resource("http://localhost:"+GestionnaireGameEngine.PORT+"/GameEngine/resources/players/leaderboard");
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
    
    /**
     * Permet de récupérer le profil d'un utilisateur avec ses informations liées du côté GameEngine (sa liste de badges)
     * @param id id de l'entité utilisateur demandé
     * @return String un objet json comprenant le profil ou une erreur personnalisée si l'id demandé n existe pas (voir méthode getErrors dans la classe GestionnaireGameEngine)    
     * @throws JSONException
     * @throws ExceptionIdUtilisateur si l'id de l'utilisateur n'existe pas 
     */
    @GET
    @Path("{id}/profil")
    @Consumes({"application/json"})
    @Produces("application/json")
    public String profilUtilisateur(@PathParam("id") Long id) throws JSONException, ExceptionIdUtilisateur {
        
        ClientConfig cc = new DefaultClientConfig();
        Client c = Client.create(cc);
        WebResource r = c.resource("http://localhost:"+GestionnaireGameEngine.PORT+"/GameEngine/resources/players/"+id);

        ClientResponse response;
        JSONObject json;

        try {
            
            response = r.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
            json = new JSONObject(response.getEntity(String.class));
            
        } catch (UniformInterfaceException e) {
            
            String erreur = GestionnaireGameEngine.getErrors(e.getResponse());
            return erreur;
        }
        
        JSONObject jsonPrincipal = new JSONObject();

        Object badges = json.get("badges");
        
        if (badges instanceof JSONObject) {
            
            JSONObject badgesObject = json.getJSONObject("badges");

            jsonPrincipal.put("badges", badgesObject);
            
        }
        
        if (badges instanceof JSONArray) {
            
            JSONArray badgesArray = json.getJSONArray("badges");

            jsonPrincipal.put("badges", badgesArray);
            
        }
        
        Utilisateur utilisateur = utilisateurManager.find(id);
        JSONArray photoArray = new JSONArray();
        
        for(Photo photo : utilisateur.getPhotos()){
            
            JSONObject photoJSON = new JSONObject();
            photoJSON.put("titre", photo.getTitre());
            photoJSON.put("source", photo.getSource());
            photoArray.put(photoJSON);
        }
        jsonPrincipal.put("photos", photoArray);
        
        jsonPrincipal.put("nom", utilisateur.getNom());
        jsonPrincipal.put("prenom", utilisateur.getPrenom());
        jsonPrincipal.put("pseudo", utilisateur.getPseudo());
        jsonPrincipal.put("email", utilisateur.getEmail());
       
        return jsonPrincipal.toString();
       
    }
}
