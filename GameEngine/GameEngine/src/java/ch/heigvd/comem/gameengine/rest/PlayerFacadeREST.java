package ch.heigvd.comem.gameengine.rest;

import ch.heigvd.comem.gameengine.model.Player;
import ch.heigvd.comem.gameengine.services.PlayersManagerLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
/**
 *
 * @author Renouille
 */
@Stateless
@Path("players")
public class PlayerFacadeREST {
    
    @EJB
    private PlayersManagerLocal playersManagerLocal;

    @POST
    @Consumes({"application/xml", "application/json"})
    public String create(Player entity) {
        Long playerId = playersManagerLocal.create(entity.getPoints());
        
        String jsonPlayer = null;
        try {
            jsonPlayer = new JSONObject().put("playerId", playerId.toString()).toString();
        } catch (JSONException ex) {
            Logger.getLogger(PlayerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jsonPlayer;
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Player entity) {
        playersManagerLocal.update(entity.getPlayerId(), entity.getPoints());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        playersManagerLocal.remove(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Player find(@PathParam("id") Long id) {
        return playersManagerLocal.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Player> findAll() {
        return playersManagerLocal.findAll();
    }
    
    /*
    @GET
    @Path("{id}/badges")
    @Produces({"application/xml", "application/json"})
    public List<Badge> getAllBadges(@PathParam("id") Long id) {
        Player player = em.find(Player.class, id);
        
        return player.getBadges();
    }
    
    */
    
    @GET
    @Path("leaderboard")
    @Produces({"application/xml", "application/json"})
    public List<Player> getLeaderboard() {
        
        return playersManagerLocal.getLeaderboard();
    }

//    @GET
//    @Path("count")
//    @Produces({"application/xml", "application/json"})
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
}
